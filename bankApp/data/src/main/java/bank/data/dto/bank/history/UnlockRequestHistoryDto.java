package bank.data.dto.bank.history;

import bank.data.dto.bank.card.CardDto;
import bank.data.dto.security.user.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnlockRequestHistoryDto {

    private Long unlockRequestId;
    private UserDto user;
    private CardDto card;

}
