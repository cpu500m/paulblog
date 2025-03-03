package com.paulblog.repository;

import com.paulblog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.repository
 * @fileName : PostRepository
 * @date : 2025-02-23
 */
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}
