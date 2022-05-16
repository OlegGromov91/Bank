package ru.sberbank.local.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.local.component.cardComponent.BanksCommission;
import ru.sberbank.local.dto.bank.bankAccount.BankAccountDto;
import ru.sberbank.local.dto.bank.card.CardDto;
import ru.sberbank.local.dto.bank.transaction.BasicTransactionDto;
import ru.sberbank.local.dto.bank.transaction.SendingTransactionAccountDto;
import ru.sberbank.local.dto.bank.transaction.SendingTransactionCardDto;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.service.CardService;
import ru.sberbank.local.service.TransferOutsideSystemService;
import ru.sberbank.local.service.TransferWithinSystemService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Контроллер переводов
 */

@RestController()
@RequestMapping("/transfer")
public class TransferController {

    private final CardService cardService;
    private final TransferWithinSystemService transferWithinSystemService;
    private final TransferOutsideSystemService transferOutsideSystemService;

    public TransferController(CardService cardService, TransferWithinSystemService transferWithinSystemService, TransferOutsideSystemService transferOutsideSystemService) {
        this.cardService = cardService;
        this.transferWithinSystemService = transferWithinSystemService;
        this.transferOutsideSystemService = transferOutsideSystemService;
    }

    @GetMapping(path = "/commission")
    public ResponseEntity<Map<BanksCommission, BigDecimal>> loadBankCommission() {
       Map<BanksCommission, BigDecimal> commission = transferOutsideSystemService.loadBankCommission();
        return new ResponseEntity<>(commission, HttpStatus.OK);
    }

    @PutMapping(path = "/withinSystem/WithCard/{cardId}")
    public ResponseEntity<CardDto> transferMoneyWithCardWithinSystem(@PathVariable Long cardId, @RequestBody SendingTransactionCardDto sendingTransactionCardDto) {
        CardDto card = transferWithinSystemService.transferMoneyWithCardWithinSystem(cardId, sendingTransactionCardDto);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}/withinSystem/ToUserSelf/WithCard/{cardId}")
    public ResponseEntity<CardDto> transferMoneyToUserSelf(@PathVariable Long userId, @PathVariable Long cardId, @RequestBody BasicTransactionDto basicTransactionDto) {
        CardDto card = transferWithinSystemService.transferMoneyToUserSelf(userId, cardId, basicTransactionDto);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PutMapping(path = "/outsideSystem/WithCard/{cardId}")
    public ResponseEntity<CardDto> transferMoneyWithCardOutsideSystem(@PathVariable Long cardId, @RequestBody SendingTransactionCardDto sendingTransactionCardDto) {
        CardDto card = transferOutsideSystemService.transferMoneyWithCardOutsideSystem(cardId, sendingTransactionCardDto);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PutMapping(path = "/withinSystem/WithBankAccount/{bankAccountId}")
    public ResponseEntity<BankAccountDto> transferMoneyFromBankAccountToBankAccount(@PathVariable Long bankAccountId, @RequestBody SendingTransactionAccountDto sendingTransactionAccountDto) {
        BankAccountDto bankAccountDto = transferWithinSystemService.transferMoneyFromBankAccountToBankAccount(bankAccountId, sendingTransactionAccountDto);
        return new ResponseEntity<>(bankAccountDto, HttpStatus.OK);
    }

    @PutMapping(path = "/withinSystem/WithCard/{cardId}/BankAccount")
    public ResponseEntity<CardDto> transferMoneyFromCardToBankAccount(@PathVariable Long cardId, @RequestBody SendingTransactionAccountDto sendingTransactionAccountDto) {
        CardDto cardDto = transferWithinSystemService.transferMoneyFromCardToBankAccount(cardId, sendingTransactionAccountDto);
        return new ResponseEntity<>(cardDto, HttpStatus.OK);
    }


}
