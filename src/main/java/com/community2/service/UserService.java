package com.community2.service;

import com.community2.mapper.UserMapper;
import com.community2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author weijia
 * @Date 2019/9/26 13:36
 * @Version 1.0
 */
public interface UserService {

    List<User> selectOneByToken(String token);

    void creatOrUpdate(User user);

}
