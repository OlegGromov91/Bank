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
import ru.sberbank.local.model.security.user.User;

import javax.persistence.*;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("UnlockRequest")
@Table(name = "BANK_HISTORY_UNLOCK_REQUEST")
public class UnlockRequestHistory extends History {

    @Column(name = "unlock_request_id")
    private Long unlockRequestId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="userId")
    @JsonIdentityReference(alwaysAsId=true)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="cardId")
    @JsonIdentityReference(alwaysAsId=true)
    private Card card;

}
