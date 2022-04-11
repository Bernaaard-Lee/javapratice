package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetailService;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

// 데이터만 리턴해줌
@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private PrincipalDetailService principalDetailService;

/* 전통적 방법
    @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user){
        System.out.println("UserApiController:login 호출됨");
        User principal = userService.로그인(user);  // principal(접근주체)
        if (principal!=null){
            session.setAttribute("principal", principal);
        }

        return new ResponseDto<Integer>(HttpStatus.OK, 1);  // java오브젝트를 json으로 변환해서 리턴
    }

 */

    @PostMapping("/auth/joinProc")  // 새 회원을 만드는 것이므로 postmapping
    public ResponseDto<Integer> save(@RequestBody User user){       // username, password, email
        System.out.println("UserApiController:save 호출됨");
        // 실제로 DB에 insert를 하고 아래에서 return이 되면 됨
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK, 1);  // java오브젝트를 json으로 변환해서 리턴
    }

    @PutMapping("/user")    // 정보 수정이니까 putmapping
    public ResponseDto<Integer> update(@RequestBody User user, HttpSession session){       // username, password, email
        System.out.println("UserApiController:update 호출됨");
        // 실제로 DB에 insert를 하고 아래에서 return이 되면 됨
        userService.회원수정(user);

        UserDetails userDetails = principalDetailService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        return new ResponseDto<Integer>(HttpStatus.OK, 1);  // java오브젝트를 json으로 변환해서 리턴
    }
}


