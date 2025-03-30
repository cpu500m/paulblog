package com.paulblog.service;

import com.paulblog.crypto.PasswordEncoder;
import com.paulblog.crypto.ScryptPasswordEncoder;
import com.paulblog.domain.User;
import com.paulblog.exception.AlreadyExistsEmailException;
import com.paulblog.exception.InvalidSigninInformation;
import com.paulblog.httprequestdto.Login;
import com.paulblog.httprequestdto.Signup;
import com.paulblog.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.service
 * @fileName : AuthService
 * @date : 2025-03-17
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Long signin(Login login) {
        // 만약 여기서 사용자가 없다 라는 익셉션을 만들어 던진다면
        // 공격자가 사용자 없는 경우 / 비번 틀린경우 구분이 가능하므로 같은 익셉션 던짐.
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        boolean matches = encoder.matches(login.getPassword(), user.getPassword());
        if(!matches){
            throw new InvalidSigninInformation();
        }

        return user.getId();
    }

    @Transactional
    public void signup(Signup signup) {

        Optional<User> duplicatedUser = userRepository.findByEmail(signup.getEmail());

        if (duplicatedUser.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        String encryptedPassword = encoder.encrypt(signup.getPassword());

        User user = User.builder()
                .email(signup.getEmail())
                .password(encryptedPassword)
                .name(signup.getName())
                .build();

        userRepository.save(user);
    }
}
