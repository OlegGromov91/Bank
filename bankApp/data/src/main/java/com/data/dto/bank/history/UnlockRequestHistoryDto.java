package com.data.dto.bank.history;

import com.data.dto.bank.card.CardDto;
import com.data.dto.security.user.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnlockRequestHistoryDto {

    private Long unlockRequestId;
    private UserDto user;
    private CardDto card;

}
