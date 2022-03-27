package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.User;
import com.cos.blog.model.User.RoleType;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired   // bean에 등록 의존성주입
	private UserRepository userRepository;
	
	// http://localhost:8000/blog/dummy/user/1
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패했습니다. 해당 id는 DB에 없습니다."; 
		}
		return "삭제했습니다. id : " + id;
	}
	
	// password, email
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("수정에 실패했습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// 더티 체킹
		// userRepository.save(user);
		return null;
	}
	
	// http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// 한 페이지당 2건의 데이터를 리턴받음
	// 블로그 제작 시 게시글 개수를 지정할 수 있음
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		List<User> users = userRepository.findAll(pageable).getContent();
		return users;
	}
	
	// {id}주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//user/4를 찾으면 내가 데이터베이스에서 못찾아오면 user가 null이다.
		//그럼 return null이 된다.
		//그래서 Optional로 User객체를 감싸서 가지고 온다. null의 판단을 위해서
		/*
		() ->{
			return new IllegalArgumentException("해당 사용자는 없습니다.");
		}
		*/
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return null;
			}
		});	
		// 요청 : 브라우저
		// user 객체 : Java object
		// 변환 (web browser가 이해 할 수 있는 데이터) -> json(json library)
		// 만약 자바 오브젝트를 리턴하게 되면 messageConvert가 jackson library를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에 전달
		return user;
	}

	// http://localhost:8000/blog/dummy/join
	// http의 body에 username, password, email 데이터 가져옴
	@PostMapping("/dummy/join")
	public String StringJoin(User user) {
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}

}
