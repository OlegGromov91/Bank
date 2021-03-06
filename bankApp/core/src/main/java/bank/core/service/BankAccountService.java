package bank.core.service;

import bank.core.component.bankAccountComponent.bankAccountBuilder.BankAccountBuilderComponent;
import bank.data.dto.bank.bankAccount.BankAccountDto;
import bank.data.exceptions.UserNotFoundException;
import bank.data.mapper.BankAccountMapper;
import bank.data.model.bank.bankAccount.BankAccount;
import bank.data.model.bank.card.CurrencyType;
import bank.data.model.security.user.User;
import bank.data.repo.BankAccountRepository;
import bank.data.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BankAccountService {


    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final BankAccountBuilderComponent bankAccountBuilderComponent;
    private final BankAccountMapper bankAccountMapper;

    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository, BankAccountBuilderComponent bankAccountBuilderComponent, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.bankAccountBuilderComponent = bankAccountBuilderComponent;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Transactional
    @PreAuthorize("hasAuthority('bankAccount:create')")
    public BankAccountDto createNewBankAccount(Long userId) {
        User cardHolder = userRepository.findById(userId).orElseThrow(() -> {
            log.error(UserNotFoundException.DEFAULT_MESSAGE + " id " + userId);
            throw new UserNotFoundException();
        });
        BankAccount bankAccount = bankAccountBuilderComponent.buildNewBankAccount(cardHolder, CurrencyType.RUR);
        log.info("bankAccountCreated accountNumber " + bankAccount.getBeneficiaryAccount() + " userId: " + userId);
        bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDto(bankAccount);
    }


    @PreAuthorize("hasAuthority('bankAccount:create')")
    public List<BankAccountDto> findAllBankAccountsByUserId(Long userId) {
        List<BankAccount> bankAccounts = bankAccountRepository.findAllByBeneficiaryUserId(userId);
        return bankAccountMapper.toDto(bankAccounts);
    }

}
