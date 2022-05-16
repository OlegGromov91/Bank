package ru.sberbank.local.model.bank.history;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.sberbank.local.model.bank.card.Card;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("HistoryBlock")
@Table(name = "BANK_HISTORY_BLOCK")
public class HistoryBlock extends History {

    @Enumerated(EnumType.STRING)
    @Column(name = "block_reason")
    private BlockReason blockReason;

}
