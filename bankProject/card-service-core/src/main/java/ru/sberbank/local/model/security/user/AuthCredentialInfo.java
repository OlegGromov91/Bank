package ru.sberbank.local.model.security.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthCredentialInfo {

    private String login;
    private String password;

}
