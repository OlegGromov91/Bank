package com.transfer.service;


import com.data.dto.bank.card.CardDto;
import com.data.dto.bank.transaction.SendingTransactionCardDto;
import com.data.exceptions.CardNotFoundExistException;
import com.data.mapper.CardMapper;
import com.data.model.bank.BanksCommission;
import com.data.model.bank.card.Card;
import com.data.model.bank.history.OperationType;
import com.data.repo.CardRepository;
import com.history.service.HistoryService;
import com.transfer.component.BaseTransferOperationComponent;
import com.validation.transferValidator.TransferValidation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Все переводы в други банки
 */
@Service
public class TransferOutsideSystemService {


    private final TransferValidation transferValidation;
    private final CardRepository cardRepository;
    private final BaseTransferOperationComponent baseTransferOperationComponent;
    private final HistoryService historyService;
    private final CardMapper cardMapper;


    public TransferOutsideSystemService(TransferValidation transferValidation, CardRepository cardRepository, BaseTransferOperationComponent baseTransferOperationComponent, HistoryService historyService, CardMapper cardMapper) {
        this.transferValidation = transferValidation;
        this.cardRepository = cardRepository;
        this.baseTransferOperationComponent = baseTransferOperationComponent;
        this.historyService = historyService;
        this.cardMapper = cardMapper;
    }

    @PreAuthorize("hasAuthority('transaction:write')")
    public CardDto transferMoneyWithCardOutsideSystem(Long cardId, SendingTransactionCardDto sendingTransactionCardDto) {
        Card fromCard = cardRepository.findById(cardId).orElseThrow(CardNotFoundExistException::new);
        transferValidation.baseValidationForTransferWithCommissionToOutsideBank(fromCard,
                sendingTransactionCardDto.getCardRecipientNumber(),
                sendingTransactionCardDto.getMoney(),
                sendingTransactionCardDto.getCommissionBankType(),
                OperationType.TRANSFER_OUTSIDE_BANK);
        fromCard = baseTransferOperationComponent.decreaseMoneyFromWithCommission(fromCard, sendingTransactionCardDto.getMoney(), sendingTransactionCardDto.getCommissionBankType());
        cardRepository.save(fromCard);
        historyService.saveSuccessCardHistoryOperation(fromCard,
                sendingTransactionCardDto.getCardRecipientNumber(),
                "money successfully transferred",
                sendingTransactionCardDto.getMoney(),
                OperationType.TRANSFER_OUTSIDE_BANK,
                sendingTransactionCardDto.getCommissionBankType());
        return cardMapper.toDto(fromCard);
    }

    @PreAuthorize("hasAuthority('transaction:write')")
    public Map<BanksCommission, BigDecimal> loadBankCommission() {
        return Map.of(
                BanksCommission.ALPHA_BANK, BanksCommission.ALPHA_BANK.commissionAmount,
                BanksCommission.TINKOFF_BANK, BanksCommission.TINKOFF_BANK.commissionAmount);
    }


    @PreAuthorize("hasAuthority('transaction:restart')")
    public Card unlockedBlockedOperation(Long cardSenderId, Long cardRecipientId, String moneyOperation) {
        return null;
    }


}
