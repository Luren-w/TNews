package com.wzx.controller;

import com.wzx.po.User;
import com.wzx.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class LoginController {
    @Autowired
    private  UserService userService;

   @GetMapping
    public String toLogin(){
        return "admin/login";
    }

    @PostMapping("login")
    public String login(String username, String password, HttpSession session, RedirectAttributes redirectAttributes){
//        User user=userService.CheckUser(username,password);
//                if(user!=null){
//                    session.setAttribute("user",user);
//                    return "admin/index";
//                }else {
//                    redirectAttributes.addFlashAttribute("message","用户名和密码错误1");
//                    return "redirect:/admin";
//                }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            User user=(User)subject.getPrincipal();
            session.setAttribute("user",user);
            return "admin/index";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message","用户名和密码错误1");
                    return "redirect:/admin";
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "admin/login";

    }

    @RequestMapping("/unauthor")
    @ResponseBody
    public String no(){
       return "权限不足";
    }
}
