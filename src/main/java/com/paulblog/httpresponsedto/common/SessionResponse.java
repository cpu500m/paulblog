package com.paulblog.httpresponsedto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.httpresponsedto.common
 * @fileName : SessionResponse
 * @date : 2025-03-17
 */
@RequiredArgsConstructor
@Getter
public class SessionResponse {
    private final String accessToken;
}
