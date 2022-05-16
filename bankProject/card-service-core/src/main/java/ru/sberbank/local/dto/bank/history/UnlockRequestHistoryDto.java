package ru.sberbank.local.dto.bank.history;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.sberbank.local.dto.bank.card.CardDto;
import ru.sberbank.local.dto.security.user.UserDto;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.model.bank.history.History;
import ru.sberbank.local.model.security.user.User;

import javax.persistence.*;

@Data
@Builder
public class UnlockRequestHistoryDto {

    private Long unlockRequestId;
    private UserDto user;
    private CardDto card;

}
