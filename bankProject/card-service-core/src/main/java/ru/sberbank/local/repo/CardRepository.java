package ru.sberbank.local.repo;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.local.model.bank.card.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends CrudRepository<Card, Long> {


       Optional<Card> findCardByCardNumber(String cardNumber);
       List<Card> findAllByUserUserId(Long userId);
       Card findFirstByOrderByCardIdDesc();

}
