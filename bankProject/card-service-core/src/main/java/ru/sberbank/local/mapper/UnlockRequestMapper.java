package ru.sberbank.local.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.sberbank.local.dto.security.user.UnlockRequestDto;
import ru.sberbank.local.model.security.user.UnlockRequest;

import java.util.List;

@Mapper

public interface UnlockRequestMapper {

    UnlockRequestMapper INSTANCE = Mappers.getMapper(UnlockRequestMapper.class);

    @Mapping(target = "unlockRequestId", source = "unlockRequestId")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "cardId", source = "card.cardId")
    @Mapping(target = "requestActive", source = "requestActive")
    UnlockRequestDto toDto(UnlockRequest unlockRequest);

    List<UnlockRequestDto> toDto(List<UnlockRequest> unlockRequest);
}
