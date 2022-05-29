package bank.data.repo;

import bank.data.model.bank.bankAccount.BankAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    List<BankAccount> findAllByBeneficiaryUserId(Long BeneficiaryId);

    Optional<BankAccount> findByBeneficiaryAccount(String beneficiaryAccount);
}
