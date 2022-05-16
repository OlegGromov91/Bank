package ru.sberbank.local.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.local.dto.bank.bankAccount.BankAccountDto;
import ru.sberbank.local.service.BankAccountService;

import java.util.List;

/**
 * Контроллер банковских счетов
 */

@RestController()
@RequestMapping("/bankAccount")
public class BankAccountController {

    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BankAccountDto>> findAllBankAccountsByUserId(@PathVariable Long userId) {
        List<BankAccountDto> bankAccountDto = bankAccountService.findAllBankAccountsByUserId(userId);
        return new ResponseEntity<>(bankAccountDto, HttpStatus.CREATED);
    }

    @PostMapping(path = "/{userId}/bankAccounts")
    public ResponseEntity<BankAccountDto> createNewBankAccount(@PathVariable Long userId) {
        BankAccountDto bankAccountDto = bankAccountService.createNewBankAccount(userId);
        return new ResponseEntity<>(bankAccountDto, HttpStatus.CREATED);
    }


}
