package ru.sberbank.local.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.model.bank.history.History;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Long> {

    History findFirstByCardOrderByHistoryIdDesc(Card card);

    History findFirstByIsSuccessAndCard_CardIdOrderByHistoryIdDesc(Boolean isSuccess, Long cardId);
    List<History> findTop15ByCardOrderByHistoryIdDesc(Card card);
    List<History> findTop15ByCard_CardIdOrderByHistoryIdDesc(Long cardId);
    List<History> findAllByBankAccount_BankAccountId(Long bankAccountId);

}
