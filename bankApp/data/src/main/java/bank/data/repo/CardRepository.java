package bank.data.repo;

import bank.data.model.bank.card.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends CrudRepository<Card, Long> {


       Optional<Card> findCardByCardNumber(String cardNumber);
       List<Card> findAllByUserUserId(Long userId);
       Card findFirstByOrderByCardIdDesc();

}
