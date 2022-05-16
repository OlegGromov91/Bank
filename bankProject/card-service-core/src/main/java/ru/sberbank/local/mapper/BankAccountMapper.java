package ru.sberbank.local.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.sberbank.local.dto.bank.bankAccount.BankAccountDto;
import ru.sberbank.local.model.bank.bankAccount.BankAccount;

import java.util.List;

@Mapper
public interface BankAccountMapper {

    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);


    @Mapping(target = "beneficiaryId", source = "beneficiary.userId")
    @Mapping(target = "active", source = "active")
    BankAccountDto toDto(BankAccount bankAccount);

    List<BankAccountDto> toDto(List<BankAccount> bankAccounts);
}
