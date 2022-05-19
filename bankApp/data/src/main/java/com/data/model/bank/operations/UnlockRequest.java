package com.data.model.bank.operations;

import com.data.model.bank.card.Card;
import com.data.model.security.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@SuperBuilder
@Table(name = "BANK_UNLOCK_REQUEST")
@AllArgsConstructor
@NoArgsConstructor
public class UnlockRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unlock_request_id")
    private Long unlockRequestId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="userId")
    @JsonIdentityReference(alwaysAsId=true)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="cardId")
    @JsonIdentityReference(alwaysAsId=true)
    private Card card;

    @Column(name = "application_is_active", columnDefinition="boolean default true")
    private boolean isRequestActive;
}
