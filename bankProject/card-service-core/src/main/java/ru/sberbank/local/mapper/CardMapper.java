package ru.sberbank.local.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import ru.sberbank.local.dto.bank.card.CardDto;
import ru.sberbank.local.model.bank.card.Card;

import java.util.List;

@Mapper

public interface CardMapper {

    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "isBlock", source = "block")
    @Mapping(target = "isActive", source = "active")
    CardDto toDto(Card card);


    List<CardDto> toDto(List<Card> cards);
}
