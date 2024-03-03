package com.bookroles.controller;

import com.bookroles.controller.view.OutputView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: dlus91
 * @Date: 2023/9/26 14:52
 */
@Controller
@RequestMapping("/system/")
public class IndexController {

    @GetMapping("/queryIndex")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("==============进入首页=================");
        return OutputView.Index.getName();
    }

}
