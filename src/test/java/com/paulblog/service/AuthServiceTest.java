package com.paulblog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.paulblog.crypto.PasswordEncoder;
import com.paulblog.crypto.PlainPasswordEncoder;
import com.paulblog.domain.User;
import com.paulblog.exception.AlreadyExistsEmailException;
import com.paulblog.exception.InvalidSigninInformation;
import com.paulblog.httprequestdto.Login;
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
@Import(PlainPasswordEncoder.class)
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder encoder;

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
        assertEquals("1234", user.getPassword());
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

    @Test
    @DisplayName("로그인 - 성공")
    void 로그인_성공() throws Exception {
        //given
        String encryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .email("paul108203@naver.com")
                .password(encryptedPassword)
                .name("김바울")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("paul108203@naver.com")
                .password("1234")
                .build();

        //when
        Long userId = authService.signin(login);

        //then
        assertNotNull(userId);
    }
    @Test
    @DisplayName("로그인 - 실패 (비밀번호 틀림) ")
    void 로그인_실패_비밀번호_틀림() throws Exception {
        //given
        String encryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .email("paul108203@naver.com")
                .password(encryptedPassword)
                .name("김바울")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("paul108203@naver.com")
                .password("5678")
                .build();

        //expected
        assertThrows(InvalidSigninInformation.class, () ->authService.signin(login));
    }
}