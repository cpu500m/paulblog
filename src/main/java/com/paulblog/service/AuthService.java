package com.paulblog.service;

import com.paulblog.domain.User;
import com.paulblog.exception.AlreadyExistsEmailException;
import com.paulblog.httprequestdto.Signup;
import com.paulblog.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public void signup(Signup signup) {

        Optional<User> duplicatedUser = userRepository.findByEmail(signup.getEmail());

        if (duplicatedUser.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        String encryptedPassword = encoder.encode(signup.getPassword());

        User user = User.builder()
                .email(signup.getEmail())
                .password(encryptedPassword)
                .name(signup.getName())
                .build();

        userRepository.save(user);
    }
}
