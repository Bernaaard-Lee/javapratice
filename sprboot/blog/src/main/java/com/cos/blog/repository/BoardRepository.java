package com.cos.blog.repository;

import com.cos.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;


// JpaRepository 사용 시 DAO
// 자동으로 Bean 등록 
// @Repository 생략 가능
public interface BoardRepository extends  JpaRepository<Board, Integer>{
    // select * from user where username=1?;

    // JPA Naming 쿼리
    // select * from user where username=?1 and password=?2;
    // User findByUsernameAndPassword(String username, String password);

    // @Query(value="select * from user where username=?1 and password=?2, nativeQuery = true)
    // User login(String username, String password);
}
