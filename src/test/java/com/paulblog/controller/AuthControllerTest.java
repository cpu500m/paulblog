package com.paulblog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulblog.domain.Session;
import com.paulblog.domain.User;
import com.paulblog.httprequestdto.Login;
import com.paulblog.httprequestdto.Signup;
import com.paulblog.repository.SessionRepository;
import com.paulblog.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.controller
 * @fileName : AuthControllerTest
 * @date : 2025-03-17
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 - 성공")
    void 로그인_성공() throws Exception {
        //given
        userRepository.save(User.builder()
                .name("김바울")
                .email("paul108203@naver.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("paul108203@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);
        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @Transactional
    @DisplayName("로그인 - 성공 후 세션 1개 생성")
    void 로그인_성공_세션_생성() throws Exception {
        //given
        User user = userRepository.save(User.builder()
                .name("김바울")
                .email("paul108203@naver.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("paul108203@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);
        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(1L, user.getSessions().size());
    }

    @Test
    @DisplayName("로그인 - 성공 후 세션 응답")
    void 로그인_성공_세션_응답() throws Exception {
        //given
        User user = userRepository.save(User.builder()
                .name("김바울")
                .email("paul108203@naver.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("paul108203@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);
        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 - 권한이 필요한 페이지 접속")
    void 로그인_권한_필요_페이지_성공() throws Exception {
        //given
        User user = User.builder()
                .name("김바울")
                .email("paul108203@naver.com")
                .password("1234")
                .build();

        Session session = user.addSession();
        userRepository.save(user);

        //expected
        mockMvc.perform(get("/posts/foo")
                        .header("Authorization", session.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 - 권한이 필요한 페이지에 검증되지 않은 세션값으로 접속")
    void 로그인_권한_필요_페이지_실패() throws Exception {
        //given
        User user = User.builder()
                .name("김바울")
                .email("paul108203@naver.com")
                .password("1234")
                .build();

        Session session = user.addSession();
        userRepository.save(user);

        //expected
        mockMvc.perform(get("/posts/foo")
                        .header("Authorization", session.getAccessToken()+"-o123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 - 성공")
    void 회원가입_성공() throws Exception {
        //given
        Signup signup = Signup.builder()
                .email("paul108203@naver.com")
                .password("1234")
                .name("김바울")
                .build();

        //expected
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signup))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}