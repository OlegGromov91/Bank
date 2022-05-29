package bank.transfer.service;


import bank.data.dto.bank.bankAccount.BankAccountDto;
import bank.data.dto.bank.card.CardDto;
import bank.data.dto.bank.transaction.BasicTransactionDto;
import bank.data.dto.bank.transaction.SendingTransactionAccountDto;
import bank.data.dto.bank.transaction.SendingTransactionCardDto;
import bank.data.exceptions.BankAccountNotFoundExistException;
import bank.data.exceptions.CardNotFoundExistException;
import bank.data.exceptions.UserNotFoundException;
import bank.data.mapper.BankAccountMapper;
import bank.data.mapper.CardMapper;
import bank.data.model.bank.bankAccount.BankAccount;
import bank.data.model.bank.card.Card;
import bank.data.model.bank.history.OperationType;
import bank.data.model.security.user.User;
import bank.data.repo.BankAccountRepository;
import bank.data.repo.CardRepository;
import bank.data.repo.UserRepository;
import bank.history.service.HistoryService;
import bank.transfer.component.BaseTransferOperationComponent;
import bank.validation.cardValidator.CardValidator;
import bank.validation.transferValidator.TransferValidation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CardMapper cardMapper;
    private final BankAccountMapper bankAccountMapper;

    public TransferWithinSystemService(CardValidator validator,
                                       TransferValidation transferValidation,
                                       CardRepository cardRepository,
                                       UserRepository userRepository,
                                       HistoryService historyService,
                                       BaseTransferOperationComponent baseTransferOperationComponent,
                                       BankAccountRepository bankAccountRepository,
                                       CardMapper cardMapper,
                                       BankAccountMapper bankAccountMapper) {
        this.validator = validator;
        this.transferValidation = transferValidation;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.historyService = historyService;
        this.baseTransferOperationComponent = baseTransferOperationComponent;
        this.bankAccountRepository = bankAccountRepository;
        this.cardMapper = cardMapper;
        this.bankAccountMapper = bankAccountMapper;
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
        return cardMapper.toDto(fromCard);
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
        return cardMapper.toDto(fromCard);
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
        return bankAccountMapper.toDto(from);
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

        return cardMapper.toDto(card);
    }
}
