package bank.data.dto.security.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
