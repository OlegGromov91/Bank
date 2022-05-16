package ru.sberbank.local.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.sberbank.local.dto.bank.history.UnlockRequestHistoryDto;
import ru.sberbank.local.dto.security.user.UnlockRequestDto;
import ru.sberbank.local.model.bank.history.UnlockRequestHistory;
import ru.sberbank.local.model.security.user.UnlockRequest;

import java.util.List;

@Mapper

public interface UnlockRequestHistoryMapper {

    UnlockRequestHistoryMapper INSTANCE = Mappers.getMapper(UnlockRequestHistoryMapper.class);


    UnlockRequestHistoryDto toDto(UnlockRequestHistory unlockRequestHistory);
    List<UnlockRequestHistoryDto> toDto(List<UnlockRequestHistory> unlockRequest);
}
