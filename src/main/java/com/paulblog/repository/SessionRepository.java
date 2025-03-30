package com.paulblog.repository;

import com.paulblog.domain.Session;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.repository
 * @fileName : SessionRepository
 * @date : 2025-03-17
 */
public interface SessionRepository extends CrudRepository<Session, Long> {
    Optional<Session> findByAccessToken(String accessToken);
}
