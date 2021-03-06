package bank.data.mapper;

import bank.data.dto.bank.card.CardDto;
import bank.data.model.bank.card.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CardMapper {


    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "isBlock", source = "block")
    @Mapping(target = "isActive", source = "active")
    CardDto toDto(Card card);

    List<CardDto> toDto(List<Card> cards);
}
