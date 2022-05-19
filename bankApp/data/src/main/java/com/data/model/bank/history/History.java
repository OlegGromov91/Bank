package com.data.model.bank.history;

import com.data.model.bank.bankAccount.BankAccount;
import com.data.model.security.user.User;
import com.data.model.bank.card.Card;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "history_type")
@Table(name = "BANK_HISTORY")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "operation_date")
    private Timestamp operationDate;

    @Column(name = "message")
    private String message;

    @Column(name = "card_recipient_number")
    private String cardRecipientNumber;

    @Column(name = "bank_account_recipient_number")
    private String bankAccountRecipientNumber;

    @Column(name = "commission_money_amount")
    private BigDecimal commissionMoneyAmount;

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "money_amount")
    private BigDecimal moneyAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="userId")
    @JsonIdentityReference(alwaysAsId=true)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="cardId")
    @JsonIdentityReference(alwaysAsId=true)
    private Card card;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankAccount_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="bankAccountId")
    @JsonIdentityReference(alwaysAsId=true)
    private BankAccount bankAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

}
