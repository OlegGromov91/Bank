package bank.data.mapper;

import bank.data.model.bank.history.UnlockRequestHistory;
import bank.data.dto.bank.history.UnlockRequestHistoryDto;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UnlockRequestHistoryMapper {

    UnlockRequestHistoryDto toDto(UnlockRequestHistory unlockRequestHistory);

    List<UnlockRequestHistoryDto> toDto(List<UnlockRequestHistory> unlockRequest);
}
