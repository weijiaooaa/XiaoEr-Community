package com.community2.controller;

import com.community2.dto.NotificationDTO;
import com.community2.dto.QuestionDTO;
import com.community2.model.User;
import com.community2.service.NotificationService;
import com.community2.service.QuestionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model, HttpServletRequest request,
                          @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSize) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PageInfo<QuestionDTO> pageInfo = questionService.getByUserId(pageNum,pageSize,user.getId());
            //带回前端
            model.addAttribute("pageInfo",pageInfo);
        }
        if ("replies".equals(action)) {
            PageInfo<NotificationDTO> pageInfo = notificationService.getByUserId(pageNum,pageSize,user.getId());
            //带回前端
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("pageInfo",pageInfo);

        }
        return "profile";
    }
}
