package bank.core.service;

import bank.core.common.exceptions.UnlockRequestHasAlreadyBeenAcceptedException;
import com.data.dto.security.user.UnlockRequestDto;
import com.data.exceptions.CardNotFoundExistException;
import com.data.exceptions.UnlockRequestNotFoundException;
import com.data.exceptions.UserNotFoundException;
import com.data.mapper.UnlockRequestMapper;
import com.data.model.bank.card.Card;
import com.data.model.security.user.UnlockRequest;
import com.data.model.security.user.User;
import com.data.repo.CardRepository;
import com.data.repo.UnlockRequestRepository;
import com.data.repo.UserRepository;
import com.history.service.HistoryService;
import com.validation.userValidation.BaseUserValidation;
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

    @Transactional(readOnly = true)
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
