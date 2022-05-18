package com.data.repo;

import com.data.model.security.user.UnlockRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnlockRequestRepository extends CrudRepository<UnlockRequest, Long> {

    Optional<UnlockRequest> findFirstByUserUserIdAndCardCardId(Long userId, Long cardId);
}
