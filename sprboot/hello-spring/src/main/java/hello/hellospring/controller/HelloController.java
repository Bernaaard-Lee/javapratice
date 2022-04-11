package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "spring!!");
        return "hello";
    }

    @GetMapping("hello-mvc")   // name 파라미터를 주지 않으면 페이지 에러
    public String helloMvc(@RequestParam("name") String name, Model model){   // RequestParam으로 name 파라미터 값을 받음.
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody   // html의 body부분의 데이터를 직접 넣어주겠다.
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;   // name에 spring을 넣어준다면 "hello spring"이 출력
    }

    @GetMapping("hello-api")
    @ResponseBody    // 객체를 리턴하고 ResponseBody annotation을 사용하면 JSON 형태로 반환
                     // 기존 매핑을 하면 viewResolver가 동작했으나 ResponseBody가 있으면 HtttpMessageConverter가 동작함
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;     // 객체를 리턴
    }

    static class Hello{     // getter setter를 사용하는이유 : name 객체를 private으로 선언해 클래스 내에서만 작동하나
                            // getter setter를 public으로 사용하여 다른 곳에서도 호출하여 name 객체를 사용할 수 있음. (property 접근방식)
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
