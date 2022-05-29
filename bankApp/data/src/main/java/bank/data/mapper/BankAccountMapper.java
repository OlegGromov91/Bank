package bank.data.mapper;

import bank.data.dto.bank.bankAccount.BankAccountDto;
import bank.data.model.bank.bankAccount.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    @Mapping(target = "beneficiaryId", source = "beneficiary.userId")
    @Mapping(target = "active", source = "active")
    BankAccountDto toDto(BankAccount bankAccount);

    List<BankAccountDto> toDto(List<BankAccount> bankAccounts);
}
