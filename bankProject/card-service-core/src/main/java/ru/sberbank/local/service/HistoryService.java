package ru.sberbank.local.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.local.component.cardComponent.BanksCommission;
import ru.sberbank.local.dto.bank.history.HistoryDto;
import ru.sberbank.local.dto.bank.history.UnlockRequestHistoryDto;
import ru.sberbank.local.dto.bank.transaction.SendingTransactionAccountDto;
import ru.sberbank.local.mapper.HistoryMapper;
import ru.sberbank.local.mapper.UnlockRequestHistoryMapper;
import ru.sberbank.local.model.bank.bankAccount.BankAccount;
import ru.sberbank.local.model.bank.history.*;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.model.security.user.UnlockRequest;
import ru.sberbank.local.repo.HistoryRepository;
import ru.sberbank.local.repo.UnlockRequestHistoryRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final UnlockRequestHistoryRepository unlockRequestHistoryRepository;

    public HistoryService(HistoryRepository historyRepository, UnlockRequestHistoryRepository unlockRequestHistoryRepository) {
        this.historyRepository = historyRepository;
        this.unlockRequestHistoryRepository = unlockRequestHistoryRepository;
    }

    @PreAuthorize("hasAuthority('history:write')")
    public void saveSuccessBankAccountHistoryOperation(BankAccount bankAccount, SendingTransactionAccountDto sendingTransactionAccountDto, String message, OperationType operationType) {
        History historyOperation = History.builder()
                .bankAccount(bankAccount)
                .bankAccountRecipientNumber(sendingTransactionAccountDto.getBeneficiaryAccount())
                .user(bankAccount.getBeneficiary())
                .operationType(operationType)
                .operationDate(Timestamp.valueOf(LocalDateTime.now()))
                .message(message)
                .isSuccess(true)
                .moneyAmount(new BigDecimal(sendingTransactionAccountDto.getMoney()))
                .build();
        historyRepository.save(historyOperation);
    }

    @PreAuthorize("hasAuthority('history:write')")
    public void saveSuccessFromCardToBankAccountHistoryOperation(Card card, SendingTransactionAccountDto sendingTransactionAccountDto, String message, OperationType operationType) {
        History historyOperation = History.builder()
                .card(card)
                .bankAccountRecipientNumber(sendingTransactionAccountDto.getBeneficiaryAccount())
                .user(card.getUser())
                .operationType(operationType)
                .operationDate(Timestamp.valueOf(LocalDateTime.now()))
                .message(message)
                .isSuccess(true)
                .moneyAmount(new BigDecimal(sendingTransactionAccountDto.getMoney()))
                .build();
        historyRepository.save(historyOperation);
    }

    @PreAuthorize("hasAuthority('history:write')")
    public void saveSuccessCardHistoryOperation(Card card,
                                                String cardRecipientNumber,
                                                String message,
                                                String moneyOperation,
                                                OperationType operationType,
                                                String commissionBankType) {
        History historyOperation = History.builder()
                .card(card)
                .cardRecipientNumber(cardRecipientNumber)
                .user(card.getUser())
                .operationType(operationType)
                .commissionMoneyAmount(BanksCommission.valueOf(commissionBankType).commissionAmount)
                .operationDate(Timestamp.valueOf(LocalDateTime.now()))
                .message(message)
                .isSuccess(true)
                .moneyAmount(new BigDecimal(moneyOperation))
                .build();

        historyRepository.save(historyOperation);
    }

    @PreAuthorize("hasAuthority('history:write')")
    public void saveSuccessCardHistoryOperation(Card card, String message, String moneyOperation, OperationType operationType) {
        History historyOperation = History.builder()
                .card(card)
                .user(card.getUser())
                .operationDate(Timestamp.valueOf(LocalDateTime.now()))
                .message(message)
                .isSuccess(true)
                .operationType(operationType)
                .moneyAmount(new BigDecimal(moneyOperation))
                .build();
        historyRepository.save(historyOperation);
    }

    @PreAuthorize("hasAuthority('history:write')")
    public void saveFrodHistoryOperation(Card cardSender, String cardRecipientNumber, String message, String moneyOperation, BlockReason blockReason, OperationType operationType) {
        HistoryBlock historyOperation = HistoryBlock.builder()
                .blockReason(blockReason)
                .cardRecipientNumber(cardRecipientNumber)
                .card(cardSender)
                .moneyAmount(new BigDecimal(moneyOperation))
                .message(message)
                .isSuccess(false)
                .operationType(operationType)
                .user(cardSender.getUser())
                .operationDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        historyRepository.save(historyOperation);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('history:read')")
    public List<HistoryDto> getHistoryOperationWithBankAccount(Long bankAccountId) {
        List<History> bankAccountHistory = historyRepository.findAllByBankAccount_BankAccountId(bankAccountId);
        return HistoryMapper.INSTANCE.toDto(bankAccountHistory);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('history:read')")
    public List<HistoryDto> getLastFifteenHistoryOperationWithCard(Long cardId) {
        List<History> lastCardHistory = historyRepository.findTop15ByCard_CardIdOrderByHistoryIdDesc(cardId);
        return HistoryMapper.INSTANCE.toDto(lastCardHistory);
    }

    @Transactional()
    @PreAuthorize("hasAuthority('history:write')")
    public void saveUnlockRequestHistory(UnlockRequest unlockRequest) {
        UnlockRequestHistory unlockRequestHistory = UnlockRequestHistory.builder()
                .card(unlockRequest.getCard())
                .user(unlockRequest.getUser())
                .unlockRequestId(unlockRequest.getUnlockRequestId())
                .operationDate(Timestamp.valueOf(LocalDateTime.now()))
                .operationType(OperationType.UNLOCK_CARD)
                .build();
        historyRepository.save(unlockRequestHistory);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('history:read')")
    public List<UnlockRequestHistoryDto> getUnlockRequestHistory() {
        List<UnlockRequestHistory> history = (List<UnlockRequestHistory>)unlockRequestHistoryRepository.findAll();
        return UnlockRequestHistoryMapper.INSTANCE.toDto(history);
    }


}
