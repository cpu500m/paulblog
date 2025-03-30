package com.paulblog.httprequestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.httprequestdto
 * @fileName : Login
 * @date : 2025-03-17
 */

@ToString
@Getter
@Builder
public class Login {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}