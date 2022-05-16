package ru.sberbank.local.dto.bank.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardValidationDto {

   private String pinCode;
   private String cvv;

}
