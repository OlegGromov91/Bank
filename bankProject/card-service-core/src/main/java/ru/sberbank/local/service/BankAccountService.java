package ru.sberbank.local.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.local.comons.exceptions.UserNotFoundException;
import ru.sberbank.local.component.bankAccountComponent.bankAccountBuilder.BankAccountBuilderComponent;
import ru.sberbank.local.dto.bank.bankAccount.BankAccountDto;
import ru.sberbank.local.mapper.BankAccountMapper;
import ru.sberbank.local.model.bank.bankAccount.BankAccount;
import ru.sberbank.local.model.bank.card.CurrencyType;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.repo.BankAccountRepository;
import ru.sberbank.local.repo.UserRepository;

import java.util.List;

@Service
public class BankAccountService {


    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final BankAccountBuilderComponent bankAccountBuilderComponent;

    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository, BankAccountBuilderComponent bankAccountBuilderComponent) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.bankAccountBuilderComponent = bankAccountBuilderComponent;
    }

    @Transactional
    @PreAuthorize("hasAuthority('bankAccount:create')")
    public BankAccountDto createNewBankAccount(Long userId) {
        User cardHolder = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        BankAccount bankAccount = bankAccountBuilderComponent.buildNewBankAccount(cardHolder, CurrencyType.RUR);
        bankAccountRepository.save(bankAccount);
        return BankAccountMapper.INSTANCE.toDto(bankAccount);
    }

    @Transactional
    @PreAuthorize("hasAuthority('bankAccount:create')")
    public List<BankAccountDto> findAllBankAccountsByUserId(Long userId) {
        List<BankAccount> bankAccounts = bankAccountRepository.findAllByBeneficiaryUserId(userId);
        return BankAccountMapper.INSTANCE.toDto(bankAccounts);
    }

}
