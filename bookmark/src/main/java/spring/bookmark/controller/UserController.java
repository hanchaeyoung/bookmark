package spring.bookmark.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.bookmark.domain.User;
import spring.bookmark.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/list")
    public String userList(Model model) {
        // 사용자 목록을 가져오는 서비스 메서드 호출
        List<User> userList = userService.getAllUsers();

        // 모델에 사용자 목록 추가
        model.addAttribute("userList", userList);

        // user/bookList.html 페이지로 이동
        return "user/list";
    }



    @RequestMapping("/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/add-save")
    public String addSave(
            @RequestParam(value = "id") String id
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "username") String username
            , @RequestParam(value = "address") String address
            , @RequestParam(value = "email") String email) {
        User user = User.build(id, password, username, address, email);
        userService.add(user);

        return "user/add-save";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "user/mypage";
    }

    @RequestMapping("/userlist")
    private String home(Model model) {

        // 사용자 목록을 가져오는 서비스 메서드 호출
        List<User> userList = userService.getAllUsers();

        // 모델에 사용자 목록 추가
        model.addAttribute("userList", userList);

        return "user/userList";
    }
}
