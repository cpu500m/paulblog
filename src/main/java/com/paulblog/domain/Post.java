package com.paulblog.domain;

import com.paulblog.domain.PostEditor.PostEditorBuilder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.domain
 * @fileName : Post
 * @date : 2025-02-23
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEditorBuilder toEditor(){
        return PostEditor.builder()
                .title(this.title)
                .content(this.content);
    }

    public void edit(PostEditor postEditor){
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }
}