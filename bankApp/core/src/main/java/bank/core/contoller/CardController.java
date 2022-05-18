package bank.core.contoller;

import bank.core.service.CardService;
import com.data.dto.bank.card.CardDto;
import com.data.dto.bank.card.CardValidationDto;
import com.data.dto.bank.transaction.BasicTransactionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер банковских карт
 */

@RestController()
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;


    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping(path = "/{cardId}")
    public ResponseEntity<CardDto> getCardById(@PathVariable Long cardId) {
        CardDto card = cardService.getCardById(cardId);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<CardDto>> showCards(@PathVariable Long userId) {
        List<CardDto> cards = cardService.showCards(userId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PostMapping(path = "/{cardId}/pinValidate")
    public ResponseEntity<HttpStatus> validatePinCode(@PathVariable Long cardId, @RequestBody CardValidationDto cardValidationDto) {
        cardService.checkCardPinCode(cardId, cardValidationDto.getPinCode());
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/user/{userId}/newCard")
    public ResponseEntity<CardDto> createNewCard(@PathVariable Long userId, @RequestBody CardDto input) {
        CardDto card = cardService.createNewCard(userId,
                input.getPinCode(),
                input.getCardType().name(),
                input.getCurrencyType().name(),
                input.getPaymentSystem().name());
        return new ResponseEntity<>(card, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{cardId}/increaseBalance")
    public ResponseEntity<BasicTransactionDto> increaseBalance(@PathVariable Long cardId, @RequestBody BasicTransactionDto basicTransactionDto) {
        return new ResponseEntity<>(cardService.increaseBalance(cardId, basicTransactionDto), HttpStatus.ACCEPTED);
    }

}
