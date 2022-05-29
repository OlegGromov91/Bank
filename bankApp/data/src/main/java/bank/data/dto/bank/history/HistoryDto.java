package bank.data.dto.bank.history;

import bank.data.dto.bank.bankAccount.BankAccountDto;
import bank.data.dto.bank.card.CardDto;
import bank.data.dto.security.user.UserDto;
import bank.data.model.bank.history.BlockReason;
import bank.data.model.bank.history.OperationType;
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
