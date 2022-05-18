package com.data.dto.security.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
