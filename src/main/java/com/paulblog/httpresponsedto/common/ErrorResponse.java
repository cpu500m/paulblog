package com.paulblog.httpresponsedto.common;

import java.util.Map;
import lombok.Builder;

/**
 * @author : paulkim
 * @description : { "code" : "400", "message" : "잘못된 요청입니다.", "validation": { "title": "값을 입력해주세요" }
 * }
 * @packageName : com.paulblog.httpresponsedto
 * @fileName : ErrorResponse
 * @date : 2025-02-17
 */

//@JsonInclude(value = Include.NON_EMPTY) -- 비어있는 정보는 응답으로 안보내고 싶다면 다음 설정 사용.
@Builder
public record ErrorResponse(
        String code,
        String message,
        Map<String, String> validation
) {

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}