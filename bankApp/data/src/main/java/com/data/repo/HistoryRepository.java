package com.data.repo;

import com.data.model.bank.card.Card;
import com.data.model.bank.history.History;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Long> {

    History findFirstByCardOrderByHistoryIdDesc(Card card);

    History findFirstByIsSuccessAndCard_CardIdOrderByHistoryIdDesc(Boolean isSuccess, Long cardId);
    List<History> findTop15ByCardOrderByHistoryIdDesc(Card card);
    List<History> findTop15ByCard_CardIdOrderByHistoryIdDesc(Long cardId);
    List<History> findAllByBankAccount_BankAccountId(Long bankAccountId);

}
