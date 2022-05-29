package bank.core.service;

import bank.core.common.exceptions.UnlockRequestHasAlreadyBeenAcceptedException;
import bank.data.dto.security.user.UnlockRequestDto;
import bank.data.exceptions.CardNotFoundExistException;
import bank.data.exceptions.UnlockRequestNotFoundException;
import bank.data.exceptions.UserNotFoundException;
import bank.data.mapper.UnlockRequestMapper;
import bank.data.model.bank.card.Card;
import bank.data.model.bank.operations.UnlockRequest;
import bank.data.model.security.user.User;
import bank.data.repo.CardRepository;
import bank.data.repo.UnlockRequestRepository;
import bank.data.repo.UserRepository;
import bank.history.service.HistoryService;
import bank.validation.userValidation.BaseUserValidation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnlockRequestService {

    private final UnlockRequestRepository unlockRequestRepository;
    private final CardRepository cardRepository;
    private final HistoryService historyService;
    private final UserRepository userRepository;
    private final BaseUserValidation baseUserValidation;
    private final UnlockRequestMapper unlockRequestMapper;

    public UnlockRequestService(UnlockRequestRepository unlockRequestRepository, CardRepository cardRepository, HistoryService historyService, UserRepository userRepository, BaseUserValidation baseUserValidation, UnlockRequestMapper unlockRequestMapper) {
        this.unlockRequestRepository = unlockRequestRepository;
        this.cardRepository = cardRepository;
        this.historyService = historyService;
        this.userRepository = userRepository;
        this.baseUserValidation = baseUserValidation;
        this.unlockRequestMapper = unlockRequestMapper;
    }


    @PreAuthorize("hasAuthority('card:unlock')")
    public List<UnlockRequestDto> showUnlockRequests() {
        List<UnlockRequest> unlockRequests = (List<UnlockRequest>) unlockRequestRepository.findAll();
        return unlockRequestMapper.toDto(unlockRequests);
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
