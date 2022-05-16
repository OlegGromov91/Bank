package ru.sberbank.local.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.local.comons.exceptions.CardNotFoundExistException;
import ru.sberbank.local.comons.exceptions.UnlockRequestHasAlreadyBeenAcceptedException;
import ru.sberbank.local.comons.exceptions.UnlockRequestNotFoundException;
import ru.sberbank.local.comons.exceptions.UserNotFoundException;
import ru.sberbank.local.component.userComponent.validation.BaseUserValidation;
import ru.sberbank.local.dto.security.user.UnlockRequestDto;
import ru.sberbank.local.mapper.CardMapper;
import ru.sberbank.local.mapper.UnlockRequestMapper;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.model.security.user.UnlockRequest;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.repo.CardRepository;
import ru.sberbank.local.repo.HistoryRepository;
import ru.sberbank.local.repo.UnlockRequestRepository;
import ru.sberbank.local.repo.UserRepository;

import java.util.List;

@Service
public class UnlockRequestService {

    private final UnlockRequestRepository unlockRequestRepository;
    private final CardRepository cardRepository;
    private final HistoryService historyService;
    private final UserRepository userRepository;
    private final BaseUserValidation baseUserValidation;

    public UnlockRequestService(UnlockRequestRepository unlockRequestRepository, CardRepository cardRepository, HistoryService historyService, UserRepository userRepository, BaseUserValidation baseUserValidation) {
        this.unlockRequestRepository = unlockRequestRepository;
        this.cardRepository = cardRepository;
        this.historyService = historyService;
        this.userRepository = userRepository;
        this.baseUserValidation = baseUserValidation;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('card:unlock')")
    public List<UnlockRequestDto> showUnlockRequests() {
        List<UnlockRequest> unlockRequests = (List<UnlockRequest>) unlockRequestRepository.findAll();
        return UnlockRequestMapper.INSTANCE.toDto(unlockRequests);
    }

    @Transactional()
    @PreAuthorize("hasAuthority('card:unlock')")
    public void unlockUserCard(UnlockRequestDto unlockRequestDto) {
        Card userCard = cardRepository.findById(unlockRequestDto.getCardId()).orElseThrow(CardNotFoundExistException::new);
        userCard.setBlock(false);
        cardRepository.save(userCard);
        UnlockRequest request = unlockRequestRepository.findById(unlockRequestDto.getUnlockRequestId()).orElseThrow(UnlockRequestNotFoundException::new);
        historyService.saveUnlockRequestHistory(request);
        unlockRequestRepository.delete(request);
    }

    @Transactional()
    @PreAuthorize("hasAuthority('card:write')")
    public void sendRequestForCardUnlock(UnlockRequestDto unlockRequestDto) {
        User user = userRepository.findById(unlockRequestDto.getUserId()).orElseThrow(UserNotFoundException::new);
        baseUserValidation.isUserSecretWordValid(unlockRequestDto.getSecretWord(), user.getSecretWord());
        unlockRequestRepository.findFirstByUserUserIdAndCardCardId(unlockRequestDto.getUserId(), unlockRequestDto.getCardId()).ifPresent((request) -> {
            throw new UnlockRequestHasAlreadyBeenAcceptedException(UnlockRequestHasAlreadyBeenAcceptedException.DEFAULT_MESSAGE + " requestId " + request.getUnlockRequestId());
        });
        Card card = cardRepository.findById(unlockRequestDto.getCardId()).orElseThrow(CardNotFoundExistException::new);
        UnlockRequest unlockRequest = UnlockRequest.builder()
                .card(card)
                .user(user)
                .isRequestActive(true)
                .build();
        unlockRequestRepository.save(unlockRequest);
    }

}
