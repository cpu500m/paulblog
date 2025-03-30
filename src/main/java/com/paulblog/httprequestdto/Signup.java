package com.paulblog.httprequestdto;

import lombok.Builder;
import lombok.Getter;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.httprequestdto
 * @fileName : Signup
 * @date : 2025-03-30
 */

@Builder
@Getter
public class Signup {
    private String name;
    private String email;
    private String password;
}