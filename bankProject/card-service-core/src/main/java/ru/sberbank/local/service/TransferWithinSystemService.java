package ru.sberbank.local.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.local.comons.exceptions.BankAccountNotFoundExistException;
import ru.sberbank.local.comons.exceptions.CardNotFoundExistException;
import ru.sberbank.local.comons.exceptions.UserNotFoundException;
import ru.sberbank.local.component.Validator;
import ru.sberbank.local.component.cardComponent.validation.CardValidator;
import ru.sberbank.local.component.trransferComponent.BaseTransferOperationComponent;
import ru.sberbank.local.component.trransferComponent.TransferValidation;
import ru.sberbank.local.dto.bank.bankAccount.BankAccountDto;
import ru.sberbank.local.dto.bank.card.CardDto;
import ru.sberbank.local.dto.bank.transaction.BasicTransactionDto;
import ru.sberbank.local.dto.bank.transaction.SendingTransactionAccountDto;
import ru.sberbank.local.dto.bank.transaction.SendingTransactionCardDto;
import ru.sberbank.local.mapper.BankAccountMapper;
import ru.sberbank.local.mapper.CardMapper;
import ru.sberbank.local.model.bank.bankAccount.BankAccount;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.model.bank.history.OperationType;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.repo.BankAccountRepository;
import ru.sberbank.local.repo.CardRepository;
import ru.sberbank.local.repo.UserRepository;

/**
 * Все переводы внутри банка
 */

@Service
public class TransferWithinSystemService {

    private final CardValidator validator;
    private final TransferValidation transferValidation;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final HistoryService historyService;
    private final BaseTransferOperationComponent baseTransferOperationComponent;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public TransferWithinSystemService(CardValidator validator,
                                       TransferValidation transferValidation,
                                       CardRepository cardRepository,
                                       UserRepository userRepository,
                                       HistoryService historyService,
                                       BaseTransferOperationComponent baseTransferOperationComponent,
                                       BankAccountRepository bankAccountRepository) {
        this.validator = validator;
        this.transferValidation = transferValidation;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.historyService = historyService;
        this.baseTransferOperationComponent = baseTransferOperationComponent;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Метод перевода с карты клиента нашего банка  на карту клиента нашего банка
     *
     * @param cardId                    карта с которой будет осуществляться первод
     * @param sendingTransactionCardDto базовые сущности необходимы для перевода
     * @return карту
     */

    @Transactional
    @PreAuthorize("hasAuthority('transaction:write')")
    public CardDto transferMoneyWithCardWithinSystem(Long cardId, SendingTransactionCardDto sendingTransactionCardDto) {
        Card fromCard = cardRepository.findById(cardId).orElseThrow(CardNotFoundExistException::new);
        transferValidation.baseValidationForTransfer(fromCard, sendingTransactionCardDto.getMoney());
        Card toCard = cardRepository.findCardByCardNumber(sendingTransactionCardDto.getCardRecipientNumber()).orElseThrow(CardNotFoundExistException::new);
        validator.isCardBlock(toCard);
        fromCard = baseTransferOperationComponent.decreaseMoneyFrom(fromCard, sendingTransactionCardDto.getMoney());
        toCard = baseTransferOperationComponent.increaseMoneyTo(toCard, sendingTransactionCardDto.getMoney());
        cardRepository.save(fromCard);
        cardRepository.save(toCard);
        historyService.saveSuccessCardHistoryOperation(fromCard, "money.successfully.transferred", sendingTransactionCardDto.getMoney(), OperationType.TRANSFER_INSIDE_BANK);
        return CardMapper.INSTANCE.toDto(fromCard);
    }

    /**
     * Метод перевода с карты на карту клиента
     *
     * @param userId              идентификатор клиента
     * @param cardId              карта с которой будет осуществляться первод
     * @param basicTransactionDto базовые сущности необходимы для перевода
     * @return карта
     */

    @Transactional
    @PreAuthorize("hasAuthority('transaction:write')")
    public CardDto transferMoneyToUserSelf(Long userId, Long cardId, BasicTransactionDto basicTransactionDto) {
        User cardHolder = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Card fromCard = cardRepository.findById(cardId).orElseThrow(CardNotFoundExistException::new);
        transferValidation.baseValidationForTransfer(fromCard, basicTransactionDto.getMoney());
        Card toCard = cardRepository.findCardByCardNumber(basicTransactionDto.getCardNumber()).orElseThrow(CardNotFoundExistException::new);
        validator.isCardBlock(toCard);
        transferValidation.baseValidationForUserSelfTransfer(toCard, cardHolder);
        fromCard = baseTransferOperationComponent.decreaseMoneyFrom(fromCard, basicTransactionDto.getMoney());
        toCard = baseTransferOperationComponent.increaseMoneyTo(toCard, basicTransactionDto.getMoney());
        cardRepository.save(fromCard);
        cardRepository.save(toCard);
        historyService.saveSuccessCardHistoryOperation(fromCard, "money.successfully.transferred", basicTransactionDto.getMoney(), OperationType.MONEY_TRANSFER_USER_SELF);
        return CardMapper.INSTANCE.toDto(fromCard);
    }


    /**
     * Метод перевода со счета на счет клиента
     *
     * @param bankAccountId                счет с которого будет осуществляться первод
     * @param sendingTransactionAccountDto базовые сущности необходимы для перевода
     * @return счет
     */

    @Transactional
    @PreAuthorize("hasAuthority('transaction:write')")
    public BankAccountDto transferMoneyFromBankAccountToBankAccount(Long bankAccountId, SendingTransactionAccountDto sendingTransactionAccountDto) {
        BankAccount from = bankAccountRepository
                .findById(bankAccountId).orElseThrow(BankAccountNotFoundExistException::new);
        validator.isMoneyEnough(sendingTransactionAccountDto.getMoney(), from.getMoneyAmount());
        BankAccount to = bankAccountRepository
                .findByBeneficiaryAccount(sendingTransactionAccountDto.getBeneficiaryAccount())
                .orElseThrow(BankAccountNotFoundExistException::new);

        from = baseTransferOperationComponent.decreaseMoneyFrom(from, sendingTransactionAccountDto.getMoney());
        to = baseTransferOperationComponent.increaseMoneyTo(to, sendingTransactionAccountDto.getMoney());
        bankAccountRepository.save(from);
        bankAccountRepository.save(to);
        historyService.saveSuccessBankAccountHistoryOperation(from, sendingTransactionAccountDto,
                "money.successfully.transferred",
                OperationType.TRANSFER_FROM_BANK_ACCOUNT_TO_BANK_ACCOUNT);
        return BankAccountMapper.INSTANCE.toDto(from);
    }

    /**
     * Метод перевода с карты на счет клиента
     *
     * @param cardId                       карта с которой будет осуществляться первод
     * @param sendingTransactionAccountDto базовые сущности необходимы для перевода
     * @return карта
     */
    @Transactional
    @PreAuthorize("hasAuthority('transaction:write')")
    public CardDto transferMoneyFromCardToBankAccount(Long cardId, SendingTransactionAccountDto sendingTransactionAccountDto) {

        Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundExistException::new);
        validator.isMoneyEnough(sendingTransactionAccountDto.getMoney(), card.getMoneyAmount());

        BankAccount bankAccount = bankAccountRepository
                .findByBeneficiaryAccount(sendingTransactionAccountDto.getBeneficiaryAccount())
                .orElseThrow(BankAccountNotFoundExistException::new);

        card = baseTransferOperationComponent.decreaseMoneyFrom(card, sendingTransactionAccountDto.getMoney());
        bankAccount = baseTransferOperationComponent.increaseMoneyTo(bankAccount, sendingTransactionAccountDto.getMoney());

        cardRepository.save(card);
        bankAccountRepository.save(bankAccount);

        historyService.saveSuccessFromCardToBankAccountHistoryOperation(card, sendingTransactionAccountDto,
                "money.successfully.transferred",
                OperationType.TRANSFER_FROM_CARD_TO_BANK_ACCOUNT);

        return CardMapper.INSTANCE.toDto(card);
    }
}
