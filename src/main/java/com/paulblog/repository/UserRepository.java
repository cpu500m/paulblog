package com.paulblog.repository;

import com.paulblog.domain.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.repository
 * @fileName : UserRepository
 * @date : 2025-03-17
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);
}
