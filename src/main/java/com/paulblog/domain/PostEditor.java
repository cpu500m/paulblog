package com.paulblog.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.domain
 * @fileName : PostEditor
 * @date : 2025-03-02
 */
@Getter
public class PostEditor {
    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}