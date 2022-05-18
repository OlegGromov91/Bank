package com.data.model.security.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthCredentialInfo {

    private String login;
    private String password;

}
