package ru.sberbank.local.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.local.dto.bank.history.HistoryDto;
import ru.sberbank.local.dto.bank.history.UnlockRequestHistoryDto;
import ru.sberbank.local.service.HistoryService;

import java.util.List;


/**
 * Контроллер истории
 */

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }


    @GetMapping("/lastCardOperations/{cardId}")
    public ResponseEntity<List<HistoryDto>> getLastFifteenHistoryOperationWithCard(@PathVariable Long cardId) {
        List<HistoryDto> lastCardHistoryDto = historyService.getLastFifteenHistoryOperationWithCard(cardId);
        return new ResponseEntity<>(lastCardHistoryDto, HttpStatus.OK);
    }

    @GetMapping("/unlockRequestHistory")
    public ResponseEntity<List<UnlockRequestHistoryDto>> getUnlockRequestHistory() {
        List<UnlockRequestHistoryDto> lastCardHistoryDto = historyService.getUnlockRequestHistory();
        return new ResponseEntity<>(lastCardHistoryDto, HttpStatus.OK);
    }

    @GetMapping("/lastBankAccountOperations/{bankAccountId}")
    public ResponseEntity<List<HistoryDto>> getHistoryOperationWithBankAccount(@PathVariable Long bankAccountId) {
        List<HistoryDto> lastCardHistoryDto = historyService.getHistoryOperationWithBankAccount(bankAccountId);
        return new ResponseEntity<>(lastCardHistoryDto, HttpStatus.OK);
    }

}
