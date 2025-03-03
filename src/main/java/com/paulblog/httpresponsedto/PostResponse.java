package com.paulblog.httpresponsedto;

import com.paulblog.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

/**
 * @author : paulkim
 * @description : 정책으로 title 은 최대 10글자만 리턴하게 해라. 라고 정해진 상황.
 * @packageName : com.paulblog.httpresponsedto
 * @fileName : PostResponse
 * @date : 2025-02-28
 */

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;

    public static PostResponse from(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle().substring(0,Math.min(post.getTitle().length(),10)))
                .content(post.getContent())
                .build();
    }
}