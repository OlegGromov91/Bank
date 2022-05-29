package bank.data.model.bank.history;

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
@DiscriminatorValue("UnlockRequest")
@Table(name = "BANK_HISTORY_UNLOCK_REQUEST")
public class UnlockRequestHistory extends History {

    @Column(name = "unlock_request_id")
    private Long unlockRequestId;

}
