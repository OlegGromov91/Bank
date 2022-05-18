package com.transfer.controller;

import com.data.dto.bank.bankAccount.BankAccountDto;
import com.data.dto.bank.card.CardDto;
import com.data.dto.bank.transaction.BasicTransactionDto;
import com.data.dto.bank.transaction.SendingTransactionAccountDto;
import com.data.dto.bank.transaction.SendingTransactionCardDto;
import com.data.model.bank.BanksCommission;
import com.transfer.service.TransferOutsideSystemService;
import com.transfer.service.TransferWithinSystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Контроллер переводов
 */

@RestController()
@RequestMapping("/transfer")
public class TransferController {


    private final TransferWithinSystemService transferWithinSystemService;
    private final TransferOutsideSystemService transferOutsideSystemService;

    public TransferController(TransferWithinSystemService transferWithinSystemService, TransferOutsideSystemService transferOutsideSystemService) {
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
