package ru.sberbank.local.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.sberbank.local.dto.bank.history.HistoryDto;
import ru.sberbank.local.model.bank.history.History;

import java.util.List;

@Mapper

public interface HistoryMapper {

    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);

    @Mapping(target = "cardRecipientNumber", source = "cardRecipientNumber")
    @Mapping(target = "operationType", source = "operationType")
    @Mapping(target = "bankAccountRecipientNumber", source = "bankAccountRecipientNumber")
    HistoryDto toDto(History card);

    List<HistoryDto> toDto(List<History> cards);

}
