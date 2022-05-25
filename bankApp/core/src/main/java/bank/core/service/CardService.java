package bank.core.service;

import bank.core.component.cardComponent.CardBuilderComponent;
import com.data.dto.bank.card.CardDto;
import com.data.dto.bank.transaction.BasicTransactionDto;
import com.data.exceptions.CardNotFoundExistException;
import com.data.exceptions.UserNotFoundException;
import com.data.mapper.CardMapper;
import com.data.model.bank.card.Card;
import com.data.model.bank.history.OperationType;
import com.data.model.security.user.User;
import com.data.repo.CardRepository;
import com.data.repo.UserRepository;
import com.history.service.HistoryService;
import com.validation.cardValidator.CardSecurityValidatorComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class CardService {


    private final CardSecurityValidatorComponent cardSecurityValidatorComponent;
    private final CardBuilderComponent cardBuilderComponent;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final HistoryService historyService;
    private final CardMapper cardMapper;


    public CardService(CardSecurityValidatorComponent cardSecurityValidatorComponent, CardBuilderComponent cardBuilderComponent, CardRepository cardRepository, UserRepository userRepository, HistoryService historyService, CardMapper cardMapper) {
        this.cardSecurityValidatorComponent = cardSecurityValidatorComponent;
        this.cardBuilderComponent = cardBuilderComponent;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.historyService = historyService;
        this.cardMapper = cardMapper;
    }

    @Transactional
    @PreAuthorize("hasAuthority('card:create')")
    public CardDto createNewCard(Long userId, String pinCode, String cardType, String currencyType, String paySystem) {
        User cardHolder = userRepository.findById(userId).orElseThrow(() -> {
            log.error(UserNotFoundException.DEFAULT_MESSAGE + " id " + userId);
            throw new UserNotFoundException();
        });
        Card card = cardBuilderComponent.buildNewCard(cardHolder, pinCode, cardType, currencyType, paySystem);
        log.info("cardCreated cardNumber " + card.getCardNumber() + " userId: " + userId);
        cardRepository.save(card);
        return cardMapper.toDto(card);
    }

    @Transactional
    @PreAuthorize("hasAuthority('card:write')")
    public BasicTransactionDto increaseBalance(Long cardId, BasicTransactionDto basicTransactionDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> {
            log.error(CardNotFoundExistException.DEFAULT_MESSAGE + " id " + cardId);
            throw new CardNotFoundExistException();
        });
        card.setMoneyAmount(card.getMoneyAmount().add(new BigDecimal(basicTransactionDto.getMoney())));
        log.info("balanceIncreased cardId " + cardId + " money: " + basicTransactionDto.getMoney());
        cardRepository.save(card);
        historyService.saveSuccessCardHistoryOperation(card,
                "money.successfully.transferred",
                basicTransactionDto.getMoney(),
                OperationType.INCREASE_CARD_BALANCE);
        basicTransactionDto.setCard(card);
        return basicTransactionDto;
    }


    @PreAuthorize("hasAuthority('card:read')")
    public List<CardDto> showCards(Long userId) {
        List<Card> cards = cardRepository.findAllByUserUserId(userId);
        return cardMapper.toDto(cards);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('card:read')")
    public CardDto getCardById(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> {
            log.error(CardNotFoundExistException.DEFAULT_MESSAGE + " id " + cardId);
            throw new CardNotFoundExistException();
        });
        return cardMapper.toDto(card);
    }


    @PreAuthorize("hasAuthority('card:read')")
    public void checkCardPinCode(Long cardId, String pinCode) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> {
            log.error(CardNotFoundExistException.DEFAULT_MESSAGE + " id " + cardId);
            throw new CardNotFoundExistException();
        });
        cardSecurityValidatorComponent.pinIsValid(pinCode, card.getPinCodeHash());
    }

}
