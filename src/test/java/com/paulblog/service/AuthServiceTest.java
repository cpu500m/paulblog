package com.paulblog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.paulblog.domain.User;
import com.paulblog.exception.AlreadyExistsEmailException;
import com.paulblog.httprequestdto.Signup;
import com.paulblog.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.service
 * @fileName : AuthServiceTest
 * @date : 2025-03-30
 */

@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 - 성공")
     void 회원가입_성공() throws Exception {
        //given
        Signup signup = Signup.builder()
                .name("김바울")
                .password("1234")
                .email("paul108203@naver.com")
                .build();

        //when
        authService.signup(signup);

        //then
        assertEquals(1,userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("paul108203@naver.com", user.getEmail());
        assertEquals("김바울", user.getName());
    }
    

    @Test
    @DisplayName("회원가입 - 실패 (중복된 이메일)")
     void 회원가입_실패_중복된_이메일() throws Exception {
        //given
        User preAssignedUser = User.builder()
                .email("paul108203@naver.com")
                .password("1234")
                .name("바울킴")
                .build();

        userRepository.save(preAssignedUser);

        Signup signup = Signup.builder()
                .name("김바울")
                .password("1234")
                .email("paul108203@naver.com")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class,
                () -> authService.signup(signup));
    }
}