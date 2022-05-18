package com.data.mapper;

import com.data.dto.bank.history.HistoryDto;
import com.data.model.bank.history.History;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface HistoryMapper {


    @Mapping(target = "cardRecipientNumber", source = "cardRecipientNumber")
    @Mapping(target = "operationType", source = "operationType")
    @Mapping(target = "bankAccountRecipientNumber", source = "bankAccountRecipientNumber")
    HistoryDto toDto(History card);

    List<HistoryDto> toDto(List<History> cards);

}
