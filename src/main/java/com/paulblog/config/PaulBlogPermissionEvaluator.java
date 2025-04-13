package com.paulblog.config;

import com.paulblog.domain.Post;
import com.paulblog.exception.PostNotFound;
import com.paulblog.repository.PostRepository;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.config
 * @fileName : PaulBlogPermissionEvaluator
 * @date : 2025-04-06
 */
@Slf4j
@RequiredArgsConstructor
public class PaulBlogPermissionEvaluator implements PermissionEvaluator {

    private final PostRepository postRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject,
            Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId,
            String targetType, Object permission) {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        Post post = postRepository.findById((Long) targetId)
                .orElseThrow(PostNotFound::new);

        if(!post.getUser().getId().equals(principal.getUserId())){
            log.error("[인가실패] 해당 사용자가 작성한 글이 아닙니다. targetId={}", targetId);
            return false;
        }

        return true;
    }
}
