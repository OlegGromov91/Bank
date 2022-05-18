package com.data.dto.bank.history;

import com.data.dto.bank.bankAccount.BankAccountDto;
import com.data.dto.bank.card.CardDto;
import com.data.dto.security.user.UserDto;
import com.data.model.bank.history.BlockReason;
import com.data.model.bank.history.OperationType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

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
