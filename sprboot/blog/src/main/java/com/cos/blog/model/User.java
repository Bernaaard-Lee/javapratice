package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// ORM -> Java(다른언어) Object -> 테이블 매핑해주는 기술
@Entity  // user 클래스를 mysql 테이블로 생성
@Table(name="user")
// @DynamicInsert // null인 필드 제외
public class User {
	@Id 	// primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY)   // 프로젝트에서 4가지 전략중 1개, DB의 넘버링 표시
	private int id;
	
	@Column(nullable=false, length=30, unique = true) 
	private String username;
	
	@Column(nullable=false, length=100)  // length를 100으로 준 이유 : 123456 => 해쉬코드화 (비밀번호 암호화)
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	
	// DB는 RoleType이라는 것이 없다
	// @ColumnDefault("user")
	@Enumerated(EnumType.STRING)
	private RoleType role;			// enum을 쓰는게 좋다 (admin, user)
	
	// 내가 직접 시간을 입력하려면 Timestamp.valuOf(LocalDateTime.now())
	@CreationTimestamp    // 시간 자동 입력
	private Timestamp createDate;
	
	
	public enum RoleType {
		ADMIN, USER;
	}
	// in yml, use-new-id-generator-mappings: false : JPA 넘버링 전략 or 프로젝트 전략 false로 지정할 경우 프로젝트의 넘버링 전략을 따라감
}
