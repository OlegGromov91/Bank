package ru.sberbank.local.component.bankAccountComponent.bankAccountBuilder;

import org.springframework.stereotype.Component;
import ru.sberbank.local.component.bankAccountComponent.bankAccountBuilder.BankAccountNumbersFieldBuilderComponent;
import ru.sberbank.local.dto.security.user.UserDto;
import ru.sberbank.local.model.bank.bankAccount.BankAccount;
import ru.sberbank.local.model.bank.bankAccount.CorrespondentBankType;
import ru.sberbank.local.model.bank.card.CardType;
import ru.sberbank.local.model.bank.card.CurrencyType;
import ru.sberbank.local.model.security.user.User;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.stream.Collectors;

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
