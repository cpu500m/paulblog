package com.paulblog.controller;

import com.paulblog.config.data.UserSession;
import com.paulblog.httprequestdto.PostCreate;
import com.paulblog.httprequestdto.PostEdit;
import com.paulblog.httprequestdto.PostSearch;
import com.paulblog.httpresponsedto.PostResponse;
import com.paulblog.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.controller
 * @fileName : PostController
 * @date : 2025-02-16
 */

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/foo")
    public Long foo(UserSession userSession){
        log.info(">>>{}" , userSession.id);
        return userSession.id;
    }

    @GetMapping("/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id) {
        return postService.get(id);
    }

    @GetMapping
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PostMapping
    public void post(@RequestBody @Valid PostCreate request) {
        // 1. GET Parmeter -> 별로임
        // 2. POST(body) value -> 순수한 DTO 설계가 무너짐
        // 3. Header

        request.validate();
        postService.write(request);
    }

    //todo RestControllerAdvice 에서 response 규격 수정 필요. 메시지를 back에서 가져다 쓴다고 생각하고,,
    @PostMapping("/{postId}")
    public ResponseEntity<String> edit(@PathVariable Long postId,
            @RequestBody @Valid PostEdit postEdit) {
        postService.edit(postId, postEdit);
        return ResponseEntity.ok("수정에 성공하였습니다");
    }

    @PostMapping("/{postId}/delete")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}