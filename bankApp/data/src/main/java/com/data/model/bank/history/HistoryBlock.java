package com.data.model.bank.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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
