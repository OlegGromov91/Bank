package ru.sberbank.local.model.bank.bankAccount;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.local.model.bank.card.CurrencyType;
import ru.sberbank.local.model.security.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "BANK_ACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {

    public static final String USER_PROPERTY = "beneficiary";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankAccountId;

    @Max(value = 20L)
    @Min(value = 20L)
    @Column(name = "beneficiary_account", nullable = false, length = 20)
    private String beneficiaryAccount;

    @Max(value = 9L)
    @Min(value = 9L)
    @Column(name = "bik", nullable = false, length = 9)
    private String bik;

    @Enumerated(EnumType.STRING)
    @Column(name = "correspondent_bank_type", nullable = false, length = 25)
    private CorrespondentBankType correspondentBankType;

    @Max(value = 10L)
    @Min(value = 10L)
    @Column(name = "inn", nullable = false, length = 10)
    private String inn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
    @JsonIdentityReference(alwaysAsId = true)
    private User beneficiary;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "bank_account_is_active", columnDefinition="boolean default true")
    private boolean isActive;

    @Column(name = "money_amount", columnDefinition="Decimal(10,2) default '0.00'", nullable = false)
    private BigDecimal moneyAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type", nullable = false)
    private CurrencyType currencyType;

}
