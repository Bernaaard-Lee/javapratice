package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해줌 IoC를 해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void 회원가입(User user){
        String rawPassword = user.getPassword();  // 1234 원문
        String encPassword = encoder.encode(rawPassword);  // 해쉬
        user.setPassword(encPassword);
        user.setRole(User.RoleType.USER);
        userRepository.save(user);
    }
    @Transactional
    public void 회원수정(User user) {
        // 수정 시에는 영속성 컨텍스트 user 오브젝트를 영속화시키고, 영속화된 user 오브젝트 수정
        // select를 해서 user 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서
        // 영속화된 오브젝트를 변경하면서 자동으로 DB에 update문을 날려줌

        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
           return new IllegalArgumentException("회원 찾기 실패");
        });
        String rawPassword = user.getPassword();     // user의 password를 불러와 rawPassword에 저장
        String encPassword = encoder.encode(rawPassword);  // 불러온 password를 해쉬화
        persistance.setPassword(encPassword);              // 새로운 해쉬비밀번호를 설정
        persistance.setEmail(user.getEmail());             // user의 이메일을 불러와 설정

        // 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 됨
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 전송함
    }

//    @Transactional(readOnly = true)    // select 시작할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성)
//    public User 로그인(User user){
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
}
