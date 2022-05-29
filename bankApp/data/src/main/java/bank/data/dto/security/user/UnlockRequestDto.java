package bank.data.dto.security.user;

import bank.data.dto.bank.card.CardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
