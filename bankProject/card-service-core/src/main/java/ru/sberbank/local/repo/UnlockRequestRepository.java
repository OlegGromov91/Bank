package ru.sberbank.local.repo;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.local.model.security.user.UnlockRequest;

import java.util.List;
import java.util.Optional;

public interface UnlockRequestRepository extends CrudRepository<UnlockRequest, Long> {

    Optional<UnlockRequest> findFirstByUserUserIdAndCardCardId(Long userId, Long cardId);
}
