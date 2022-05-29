package bank.data.repo;

import bank.data.model.bank.history.UnlockRequestHistory;
import org.springframework.data.repository.CrudRepository;

public interface UnlockRequestHistoryRepository extends CrudRepository<UnlockRequestHistory, Long> {



}
