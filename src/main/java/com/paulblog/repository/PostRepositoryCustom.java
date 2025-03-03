package com.paulblog.repository;

import com.paulblog.domain.Post;
import com.paulblog.httprequestdto.PostSearch;
import java.util.List;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.repository
 * @fileName : PostRepositoryCustom
 * @date : 2025-03-02
 */
public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
