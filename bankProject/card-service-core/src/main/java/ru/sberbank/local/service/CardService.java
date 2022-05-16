package ru.sberbank.local.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.local.comons.exceptions.CardNotFoundExistException;
import ru.sberbank.local.comons.exceptions.UserNotFoundException;
import ru.sberbank.local.component.cardComponent.CardBuilderComponent;
import ru.sberbank.local.component.cardComponent.validation.CardSecurityValidatorComponent;
import ru.sberbank.local.dto.bank.card.CardDto;
import ru.sberbank.local.dto.bank.transaction.BasicTransactionDto;
import ru.sberbank.local.mapper.CardMapper;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.model.bank.history.OperationType;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.repo.CardRepository;
import ru.sberbank.local.repo.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CardService {


    private final CardSecurityValidatorComponent cardSecurityValidatorComponent;
    private final CardBuilderComponent cardBuilderComponent;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final HistoryService historyService;


    public CardService(CardSecurityValidatorComponent cardSecurityValidatorComponent, CardBuilderComponent cardBuilderComponent, CardRepository cardRepository, UserRepository userRepository, HistoryService historyService) {
        this.cardSecurityValidatorComponent = cardSecurityValidatorComponent;
        this.cardBuilderComponent = cardBuilderComponent;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.historyService = historyService;
    }

    @Transactional
    @PreAuthorize("hasAuthority('card:create')")
    public CardDto createNewCard(Long userId, String pinCode, String cardType, String currencyType, String paySystem) {
        User cardHolder = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Card card = cardBuilderComponent.buildNewCard(cardHolder, pinCode, cardType, currencyType, paySystem);
        cardRepository.save(card);
        return CardMapper.INSTANCE.toDto(card);
    }

    @Transactional
    @PreAuthorize("hasAuthority('card:write')")
    public BasicTransactionDto increaseBalance(Long cardId, BasicTransactionDto basicTransactionDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundExistException::new);
        card.setMoneyAmount(card.getMoneyAmount().add(new BigDecimal(basicTransactionDto.getMoney())));
        cardRepository.save(card);
        historyService.saveSuccessCardHistoryOperation(card,
                "money.successfully.transferred",
                basicTransactionDto.getMoney(),
                OperationType.INCREASE_CARD_BALANCE);
        basicTransactionDto.setCard(card);
        return basicTransactionDto;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('card:read')")
    public List<CardDto> showCards(Long userId) {
        List<Card> cards = cardRepository.findAllByUserUserId(userId);
        return CardMapper.INSTANCE.toDto(cards);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('card:read')")
    public CardDto getCardById(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundExistException::new);
        return CardMapper.INSTANCE.toDto(card);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('card:read')")
    public boolean checkCardPinCode(Long cardId, String pinCode) {
        Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundExistException::new);
        return cardSecurityValidatorComponent.pinIsValid(pinCode, card.getPinCodeHash());
    }

}
