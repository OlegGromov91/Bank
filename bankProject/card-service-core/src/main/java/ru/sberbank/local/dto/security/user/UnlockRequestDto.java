package ru.sberbank.local.dto.security.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.local.dto.bank.card.CardDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnlockRequestDto {
   private Long unlockRequestId;
   private Long cardId;
   private Long userId;
   private CardDto card;
   private UserDto user;
   private String secretWord;
   boolean requestActive;

}
