package com.paulblog.service;

import com.paulblog.domain.Session;
import com.paulblog.domain.User;
import com.paulblog.exception.InvalidSigninInformation;
import com.paulblog.httprequestdto.Login;
import com.paulblog.repository.UserRepository;
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

    @Transactional
    public Long signin(Login login){
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        Session session = user.addSession();

        return user.getId();
    }
}
