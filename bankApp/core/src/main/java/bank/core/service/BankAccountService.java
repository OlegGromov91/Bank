package bank.core.service;

import bank.core.component.bankAccountComponent.bankAccountBuilder.BankAccountBuilderComponent;
import com.data.dto.bank.bankAccount.BankAccountDto;
import com.data.exceptions.UserNotFoundException;
import com.data.mapper.BankAccountMapper;
import com.data.model.bank.bankAccount.BankAccount;
import com.data.model.bank.card.CurrencyType;
import com.data.model.security.user.User;
import com.data.repo.BankAccountRepository;
import com.data.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        User cardHolder = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        BankAccount bankAccount = bankAccountBuilderComponent.buildNewBankAccount(cardHolder, CurrencyType.RUR);
        bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDto(bankAccount);
    }

    @Transactional
    @PreAuthorize("hasAuthority('bankAccount:create')")
    public List<BankAccountDto> findAllBankAccountsByUserId(Long userId) {
        List<BankAccount> bankAccounts = bankAccountRepository.findAllByBeneficiaryUserId(userId);
        return bankAccountMapper.toDto(bankAccounts);
    }

}