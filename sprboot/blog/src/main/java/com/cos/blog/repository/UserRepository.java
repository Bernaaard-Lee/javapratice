package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

import java.util.Optional;


// JpaRepository 사용 시 DAO
// 자동으로 Bean 등록 
// @Repository 생략 가능
public interface UserRepository extends  JpaRepository<User, Integer>{
    // select * from user where username=1?;
    Optional<User> findByUsername(String username);

    // JPA Naming 쿼리
    // select * from user where username=?1 and password=?2;
    // User findByUsernameAndPassword(String username, String password);

    // @Query(value="select * from user where username=?1 and password=?2, nativeQuery = true)
    // User login(String username, String password);
}
