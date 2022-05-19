package com.data.dto.security.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthCredentialInfo {

    private String login;
    private String password;

}
