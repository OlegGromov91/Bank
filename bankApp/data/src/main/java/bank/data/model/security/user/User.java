package bank.data.model.security.user;

import bank.data.model.bank.bankAccount.BankAccount;
import bank.data.model.bank.card.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@SuperBuilder
@Table(name = "SEC_USERS")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Pattern(regexp = "\\d{11}", message = "phone.number.is.not.valid")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "user_is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "user_login", unique = true, nullable = false)
    private String userLogin;

    @Column(name = "user_sex")
    private String sex;

    @Column(name = "user_secret_word", nullable = false)
    private String secretWord;

    @Email(message = "email.is.not.valid", regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = UserLogon.USER_PROPERTY, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserLogon> userLogon = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = Card.USER_PROPERTY, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = BankAccount.USER_PROPERTY, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BankAccount> accounts = new ArrayList<>();

}
