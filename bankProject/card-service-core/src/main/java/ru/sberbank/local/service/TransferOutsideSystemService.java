package ru.sberbank.local.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.sberbank.local.comons.exceptions.CardNotFoundExistException;
import ru.sberbank.local.component.cardComponent.BanksCommission;
import ru.sberbank.local.component.trransferComponent.BaseTransferOperationComponent;
import ru.sberbank.local.component.trransferComponent.TransferValidation;
import ru.sberbank.local.dto.bank.card.CardDto;
import ru.sberbank.local.dto.bank.transaction.SendingTransactionCardDto;
import ru.sberbank.local.mapper.CardMapper;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.model.bank.history.OperationType;
import ru.sberbank.local.repo.CardRepository;

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


    @Autowired
    public TransferOutsideSystemService(TransferValidation transferValidation,
                                        CardRepository cardRepository,
                                        BaseTransferOperationComponent baseTransferOperationComponent,
                                        HistoryService historyService) {
        this.transferValidation = transferValidation;
        this.cardRepository = cardRepository;
        this.baseTransferOperationComponent = baseTransferOperationComponent;
        this.historyService = historyService;
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
        return CardMapper.INSTANCE.toDto(fromCard);
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
