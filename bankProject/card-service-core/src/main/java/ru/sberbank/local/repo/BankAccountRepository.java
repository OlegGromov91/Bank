package ru.sberbank.local.repo;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.local.model.bank.bankAccount.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    List<BankAccount> findAllByBeneficiaryUserId(Long BeneficiaryId);

    Optional<BankAccount> findByBeneficiaryAccount(String beneficiaryAccount);
}
