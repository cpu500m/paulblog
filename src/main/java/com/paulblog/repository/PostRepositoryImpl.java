package com.paulblog.repository;

import com.paulblog.domain.Post;
import com.paulblog.domain.QPost;
import com.paulblog.httprequestdto.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.repository
 * @fileName : PostRepositoryImpl
 * @date : 2025-03-02
 */
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {

        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
