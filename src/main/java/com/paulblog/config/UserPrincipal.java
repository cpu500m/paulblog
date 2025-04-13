package com.paulblog.config;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.config
 * @fileName : UserPrincipal
 * @date : 2025-04-06
 */
public class UserPrincipal extends User {

    private final Long userId;

    // role : 역할 -> 관리자 / 사용자 / 매니저
    // authority : 권한 -> 글쓰기, 글 읽기, 사용자 정지시키기

    // Authority에 ROLE_ADMIN 이런식으로 prefix붙여서 넣으면 ROLE_ 떼고
    // 역핧: ADMIN으로 생성
    public UserPrincipal(com.paulblog.domain.User user) {
        super(user.getEmail(), user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("WRITE")));
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
