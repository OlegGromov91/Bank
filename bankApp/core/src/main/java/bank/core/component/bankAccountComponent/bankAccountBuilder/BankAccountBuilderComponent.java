package bank.core.component.bankAccountComponent.bankAccountBuilder;

import com.data.model.bank.bankAccount.BankAccount;
import com.data.model.bank.bankAccount.CorrespondentBankType;
import com.data.model.bank.card.CurrencyType;
import com.data.model.security.user.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Компонент создает банковский счет
 */
@Component
public class BankAccountBuilderComponent {

    private final BankAccountNumbersFieldBuilderComponent bankAccountNumbersFieldBuilderComponent;

    public BankAccountBuilderComponent(BankAccountNumbersFieldBuilderComponent bankAccountNumbersFieldBuilderComponent) {
        this.bankAccountNumbersFieldBuilderComponent = bankAccountNumbersFieldBuilderComponent;
    }

    /**
     * Метод создает счет для пользователя
     *
     * @param beneficiary клиент банка
     * @return новый счет
     */
    public BankAccount buildNewBankAccount(User beneficiary, CurrencyType currencyType) {

        LocalDate dateCreating = LocalDate.now();

        return BankAccount.builder()
                .beneficiaryAccount(bankAccountNumbersFieldBuilderComponent.generateBeneficiaryAccount())
                .bik(bankAccountNumbersFieldBuilderComponent.generateBik())
                .inn(bankAccountNumbersFieldBuilderComponent.generateInn())
                .correspondentBankType(CorrespondentBankType.PAO_SBERBANK_POVOLZHEY)
                .beneficiary(beneficiary)
                .currencyType(currencyType)
                .moneyAmount(new BigDecimal("0"))
                .createdDate(dateCreating)
                .expiredDate(dateCreating.plusYears(CorrespondentBankType.PAO_SBERBANK_POVOLZHEY.getExpiredDateDuration()))
                .isActive(true)
                .build();
    }


}
