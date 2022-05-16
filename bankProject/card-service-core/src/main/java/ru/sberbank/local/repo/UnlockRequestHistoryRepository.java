package ru.sberbank.local.repo;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.model.bank.history.History;
import ru.sberbank.local.model.bank.history.UnlockRequestHistory;

import java.util.List;

public interface UnlockRequestHistoryRepository extends CrudRepository<UnlockRequestHistory, Long> {



}
