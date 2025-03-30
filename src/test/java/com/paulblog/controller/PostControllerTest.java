package com.paulblog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulblog.domain.Post;
import com.paulblog.httprequestdto.PostEdit;
import com.paulblog.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author : paulkim
 * @description :1
 * @packageName : com.paulblog.controller
 * @fileName : PostControllerTest
 * @date : 2025-02-16
 */


@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("foo"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());

    }

    @Test
    @DisplayName("제목 10글자 초과 시 10글자로 줄여 반환 하는가")
    void 제목_10글자_초과시() throws Exception {
        //given
        Post post = Post.builder()
                .title("1234567890123456789")
                .content("bar")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 1페이지 조회")
    void 다건_조회() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("테스트 제목" + i)
                        .content("테스트내용" + i)
                        .build()).toList();
        postRepository.saveAll(requestPosts);

        //expect
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].title").value("테스트 제목30"))
                .andExpect(jsonPath("$[0].content").value("테스트내용30"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다")
    void 다건_조회_시작_0() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("테스트 제목" + i)
                        .content("테스트내용" + i)
                        .build()).toList();
        postRepository.saveAll(requestPosts);

        //expect
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].id").value(30))
                .andExpect(jsonPath("$[0].title").value("테스트 제목30"))
                .andExpect(jsonPath("$[0].content").value("테스트내용30"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 제목 수정")
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

        //expect
        mockMvc.perform(post("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isOk())
                .andDo(print());
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

        //expected
        mockMvc.perform(post("/posts/{postId}/delete", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(0L, postRepository.count());
    }

    @Test
    @DisplayName("단건 조회 - 존재하지 않는 글")
    void 단건_조회_예외() throws Exception {
        //expected
        mockMvc.perform(post("/posts/{postId}/delete", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정 - 존재하지 않는 글")
    void 게시글_수정_예외() throws Exception {
        //given
        PostEdit postEdit = PostEdit.builder()
                .title("변경된제목")
                .content("테스트내용")
                .build();

        //expected
        mockMvc.perform(post("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성 - 허용하지 않는 키워드")
    void 게시글_작성_비허용_키워드() throws Exception {
        //given
        Post post = Post.builder()
                .title("하하 바보같은놈")
                .content("테스트내용")
                .build();

        String json = objectMapper.writeValueAsString(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성 - 정상 동작")
    void 게시글_작성_정상() throws Exception {
        //given
        Post post = Post.builder()
                .title("하잇 제목")
                .content("테스트내용")
                .build();

        String json = objectMapper.writeValueAsString(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Post result = postRepository.findAll().get(0);
        assertEquals("하잇 제목", result.getTitle());
        assertEquals("테스트내용", result.getContent());
    }
}