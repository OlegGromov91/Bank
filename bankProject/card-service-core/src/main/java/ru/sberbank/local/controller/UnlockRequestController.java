package ru.sberbank.local.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.local.dto.security.user.UnlockRequestDto;
import ru.sberbank.local.service.UnlockRequestService;

import java.util.List;

/**
 * Контроллер разблокировки карт
 */
@RestController
@RequestMapping("/unlockRequest")
public class UnlockRequestController {


    private final UnlockRequestService unlockRequestService;


    public UnlockRequestController(UnlockRequestService unlockRequestService) {
        this.unlockRequestService = unlockRequestService;
    }

    @GetMapping()
    public ResponseEntity<List<UnlockRequestDto>> showUnlockRequests() {
        return new ResponseEntity<>(unlockRequestService.showUnlockRequests(), HttpStatus.OK);
    }

    @PutMapping("unlockCard/{cardId}")
    public ResponseEntity<?> unlockUserCard(@RequestBody UnlockRequestDto unlockRequestDto) {
        unlockRequestService.unlockUserCard(unlockRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/createRequest/{cardId}")
    public ResponseEntity<?> sendRequestForCardUnlock(@RequestBody UnlockRequestDto unlockRequestDto) {
        unlockRequestService.sendRequestForCardUnlock(unlockRequestDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
