package com.bookroles.controller;


import com.bookroles.controller.view.InputInterface;
import com.bookroles.controller.view.OutputView;
import com.bookroles.service.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: dlus91
 * @Date: 2023/9/24 10:34
 */
@Controller
@RequestMapping("/system/")
public class LoginController {

    @Autowired
    private IDictionaryService dictionaryServiceImpl;


    @GetMapping("login")
    public String loginpage() {
        System.out.println("==============登录页======================");
        return OutputView.Login.getName();
    }

    @PostMapping("login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("==============登录动作======================");
        //初始化字典表
        dictionaryServiceImpl.search();
        response.sendRedirect(InputInterface.Index.getName());
    }

    @GetMapping("logout")
    public String logoutpage() {
        System.out.println("==============登出到登录页======================");
        return OutputView.Login.getName();
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("==============注销用户session并返回登录页======================");
        return OutputView.Login.getName();
    }


}
