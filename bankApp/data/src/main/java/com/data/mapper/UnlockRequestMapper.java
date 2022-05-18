package com.data.mapper;

import com.data.dto.security.user.UnlockRequestDto;
import com.data.model.security.user.UnlockRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UnlockRequestMapper {

    @Mapping(target = "unlockRequestId", source = "unlockRequestId")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "cardId", source = "card.cardId")
    @Mapping(target = "requestActive", source = "requestActive")
    UnlockRequestDto toDto(UnlockRequest unlockRequest);

    List<UnlockRequestDto> toDto(List<UnlockRequest> unlockRequest);
}
