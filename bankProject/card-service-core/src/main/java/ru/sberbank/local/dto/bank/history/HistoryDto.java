package ru.sberbank.local.dto.bank.history;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import ru.sberbank.local.dto.bank.bankAccount.BankAccountDto;
import ru.sberbank.local.dto.bank.card.CardDto;
import ru.sberbank.local.dto.security.user.UserDto;
import ru.sberbank.local.model.bank.history.BlockReason;
import ru.sberbank.local.model.bank.history.OperationType;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
@SuperBuilder
public class HistoryDto {
    private Long historyId;
    private Timestamp operationDate;
    private String message;
    private Boolean isSuccess;
    private BigDecimal moneyAmount;
    private BigDecimal commissionMoneyAmount;
    private UserDto user;
    private CardDto card;
    private String cardRecipientNumber;
    private BlockReason blockReason;
    private OperationType operationType;
    private BankAccountDto bankAccount;
    private String bankAccountRecipientNumber;
}
