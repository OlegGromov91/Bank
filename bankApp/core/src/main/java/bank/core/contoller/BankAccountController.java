package bank.core.contoller;

import bank.core.service.BankAccountService;
import bank.data.dto.bank.bankAccount.BankAccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
