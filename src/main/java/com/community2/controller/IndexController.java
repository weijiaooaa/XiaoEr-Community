package com.community2.controller;

import com.community2.dto.QuestionDTO;
import com.community2.service.QuestionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author weijia
 * @Date 2019/9/25 19:35
 * @Version 1.0
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                         @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                         @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSize,
                         @RequestParam(value = "search", required = false) String searche){
        PageInfo<QuestionDTO> pageInfo = questionService.pageHelper(pageNum,pageSize,searche);
        //带回前端
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("search",searche);
        return "index";
    }

    @GetMapping("/loginOut")
    public  String loginOut(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        //删除cookie
        Cookie cookie = new Cookie("token", "0");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}

