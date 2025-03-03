package com.paulblog.httprequestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.httprequestdto
 * @fileName : PostEdit
 * @date : 2025-03-02
 */

public record PostEdit(
        @NotBlank(message = "타이틀을 입력하세요.") String title,
        @NotBlank(message = "내용을 입력하세요.") String content
) {
    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
