package com.cos.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data     // get, set 동시에
@NoArgsConstructor
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	

	
	@Builder // 번호 자동 생성
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
}
