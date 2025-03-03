package com.paulblog.service;

import com.paulblog.domain.Post;
import com.paulblog.domain.PostEditor;
import com.paulblog.domain.PostEditor.PostEditorBuilder;
import com.paulblog.exception.PostNotFound;
import com.paulblog.httprequestdto.PostCreate;
import com.paulblog.httprequestdto.PostEdit;
import com.paulblog.httprequestdto.PostSearch;
import com.paulblog.httpresponsedto.PostResponse;
import com.paulblog.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.service
 * @fileName : PostService
 * @date : 2025-02-23
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.from(post);
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        PostEditorBuilder postEditorBuilder = post.toEditor();

        PostEditor postEditor = postEditorBuilder.title(postEdit.title())
                .content(postEdit.content())
                .build();

        post.edit(postEditor);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        postRepository.delete(post);
    }
}
