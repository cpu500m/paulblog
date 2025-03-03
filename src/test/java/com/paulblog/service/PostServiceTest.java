package com.paulblog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.paulblog.domain.Post;
import com.paulblog.exception.PostNotFound;
import com.paulblog.httprequestdto.PostCreate;
import com.paulblog.httprequestdto.PostEdit;
import com.paulblog.httprequestdto.PostSearch;
import com.paulblog.httpresponsedto.PostResponse;
import com.paulblog.repository.PostRepository;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.service
 * @fileName : PostServiceTest
 * @date : 2025-02-23
 */

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void clear() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        //when
        postService.write(postCreate);

        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);

        assertEquals("제목", post.getTitle());
        assertEquals("내용", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() throws Exception {
        //given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        //when

        Long id = requestPost.getId();
        PostResponse post = postService.get(id);

        //then
        assertNotNull(post);
        assertEquals("foo", post.getTitle());
        assertEquals("bar", post.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void 다건_조회() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("테스트 제목" + i)
                        .content("테스트내용" + i)
                        .build()).toList();

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .build();

        //when
        List<PostResponse> posts = postService.getList(postSearch);

        //then
        assertEquals(10, posts.size());
        assertEquals("테스트 제목19", posts.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목을 정상적으로 수정한다.")
    void 글_제목_수정() throws Exception {
        //given
        Post post = Post.builder()
                .title("테스트제목")
                .content("테스트내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("변경된제목")
                .content("테스트내용")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("변경된제목", changedPost.getTitle());
    }

    @Test
    @DisplayName("게시글 수정 - 정상 동작")
    void 글_내용_수정() throws Exception {
        //given
        Post post = Post.builder()
                .title("테스트제목")
                .content("테스트내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("테스트제목")
                .content("변경된내용")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("변경된내용", changedPost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void 게시글_삭제() throws Exception {
        //given
        Post post = Post.builder()
                .title("테스트제목")
                .content("테스트내용")
                .build();
        postRepository.save(post);
        //when
        postService.delete(post.getId());

        //then
        assertEquals(0L, postRepository.count());
    }

    @Test
    @DisplayName("게시글 조회 - 없는 게시글")
    void 한건_조회_예외() throws Exception {
        //given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);

        //expected
        Long id = post.getId();
        Assertions.assertThrows(
                PostNotFound.class,
                () -> postService.get(post.getId() + 1));
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void 게시글_삭제_예외() throws Exception {
        //given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);

        //expected

        Assertions.assertThrows(
                PostNotFound.class,
                () -> postService.delete(post.getId() + 1));
    }
    @Test
    @DisplayName("게시글 수정 - 존재하지 않는 글")
    void 게시글_수정_예외() throws Exception {
        //given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("hi")
                .content("히히")
                .build();

        //expected
        Assertions.assertThrows(
                PostNotFound.class,
                () -> postService.edit(post.getId() + 1,postEdit));
    }
}