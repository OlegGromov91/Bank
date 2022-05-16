package ru.sberbank.local;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.sberbank.local.model.security.user.AuthCredentialInfo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class AuthenticationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectJsonConverter;

    @Autowired
    public AuthenticationTest(MockMvc mockMvc, ObjectMapper objectJsonConverter) {
        this.mockMvc = mockMvc;
        this.objectJsonConverter = objectJsonConverter;

    }

    @Test
    public void whenUserNotAuthenticatedThenAccessDenied() throws Exception {
        this.mockMvc.perform(get("/history/unlockRequestHistory"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void whenCredentialValidThenUserLogged() throws Exception {
        this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectJsonConverter.writeValueAsString(
                        AuthCredentialInfo.builder()
                                .login("user")
                                .password("user").build())))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    public void whenPasswordInValidThenLoginFault() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectJsonConverter.writeValueAsString(
                        AuthCredentialInfo.builder()
                                .login("user")
                                .password("BAD_VALUE").build())))
                .andDo(print())
                .andReturn();
        String resultShouldContain = String.valueOf(HttpStatus.UNAUTHORIZED.value());
        assertTrue(mvcResult.getResponse().getContentAsString().contains(resultShouldContain));
    }

    @Test
    public void whenLoginInValidThenLoginFault() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectJsonConverter.writeValueAsString(
                        AuthCredentialInfo.builder()
                                .login("user")
                                .password("BAD_VALUE").build())))
                .andDo(print())
                .andReturn();
        String resultShouldContain = String.valueOf(HttpStatus.UNAUTHORIZED.value());
        assertTrue(mvcResult.getResponse().getContentAsString().contains(resultShouldContain));
    }
}
