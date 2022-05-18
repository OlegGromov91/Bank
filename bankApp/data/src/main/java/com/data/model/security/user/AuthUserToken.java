package com.data.model.security.user;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AuthUserToken {
    private Long userId;
    private String login;
    private String token;
    private RoleType roleType;
}
