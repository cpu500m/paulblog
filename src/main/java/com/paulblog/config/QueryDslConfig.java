package com.paulblog.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.config
 * @fileName : QueryDslConfig
 * @date : 2025-03-02
 */

@Configuration
public class QueryDslConfig {

    @PersistenceContext
    public EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(em);
    }
}
