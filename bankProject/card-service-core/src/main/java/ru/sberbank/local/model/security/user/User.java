package ru.sberbank.local.model.security.user;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.sberbank.local.model.bank.bankAccount.BankAccount;
import ru.sberbank.local.model.bank.card.Card;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@SuperBuilder
@Table(name = "SEC_USERS")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
