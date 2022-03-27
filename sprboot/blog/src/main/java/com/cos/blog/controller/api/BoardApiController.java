package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

// 데이터만 리턴해줌
@RestController
public class BoardApiController {
    @Autowired
    private BoardService boardService;

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

    @PostMapping("/api/board")            // 작성이니까 deletemapping
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK, 1);  // java오브젝트를 json으로 변환해서 리턴
    }

    @DeleteMapping ("/api/board/{id}")    // 삭제니까 deletemapping
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        boardService.글삭제하기(id);
        return new ResponseDto<Integer>(HttpStatus.OK, 1);  // java오브젝트를 json으로 변환해서 리턴
    }

    @PutMapping ("/api/board/{id}")    // 수정이니까 putmapping
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
        System.out.println("BoardApiController : update : id" + id);
        System.out.println("BoardApiController : update : board" + board.getTitle());
        System.out.println("BoardApiController : update : board" + board.getContent());
        boardService.글수정하기(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK, 1);  // java오브젝트를 json으로 변환해서 리턴
    }
}


