package ru.sberbank.local.dto.security.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.local.model.security.user.RoleType;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogonDTO {
    private Long userLogonId;
    private Long userId;
    private String userLogin;
    private String passwordHash;

}
