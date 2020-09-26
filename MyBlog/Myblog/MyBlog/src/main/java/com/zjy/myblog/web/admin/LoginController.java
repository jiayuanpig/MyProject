package com.zjy.myblog.web.admin;

import com.zjy.myblog.po.User;
import com.zjy.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping//跳转登录页面
    public String loginPage(){
        return "admin/BG-login";
    }

    @PostMapping("/login")//登录方法
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if(user !=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/BG-index";
        }else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
