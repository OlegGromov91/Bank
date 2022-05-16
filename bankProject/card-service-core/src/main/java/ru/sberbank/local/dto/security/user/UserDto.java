package ru.sberbank.local.dto.security.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.local.model.security.user.RoleType;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long userId;
    private String firstName;
    private String secondName;
    private String middleName;
    private String phoneNumber;
    private boolean isActive;
    private String userLogin;
    private String email;
    private String sex;
    private String secretWord;


}
