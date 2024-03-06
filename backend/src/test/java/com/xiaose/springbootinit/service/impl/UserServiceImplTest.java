package com.xiaose.springbootinit.service.impl;

import com.xiaose.springbootinit.exception.BusinessException;
import com.xiaose.springbootinit.model.domain.User;
import com.xiaose.springbootinit.model.dto.UserRegisterDTO;
import com.xiaose.springbootinit.model.vo.UserVO;
import com.xiaose.springbootinit.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description：
 * @author：Administrator
 * @date：2023/12/29 23:30
 */

@SpringBootTest
class UserServiceImplTest {

    @Resource
    UserService userService;

    @Test
    void testAddUser(){
        User user = new User();
        user.setUsername("testXiaose");
        user.setUserAccount("123");
        user.setUserPassword("123");
        user.setAvatarUrl("https://img2.baidu.com/it/u=2708810287,2935753791&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        user.setGender(0);
        user.setPhone("123");
        user.setEmail("123@gmail.com");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);
    }

    @Test
    void userRegister() {
        UserRegisterDTO u = new UserRegisterDTO();
        u.setUserAccount("xiaose1");
        u.setUserPassword("123456789");
        u.setCheckPassword("cccccccccc");
        // long l = userService.userRegister(u);
        // Assertions.assertDoesNotThrow(new BusinessException());
        Assertions.assertDoesNotThrow( new Executable() {
            @Override
            public void execute() throws Throwable {
                long l = userService.userRegister(u);
                System.out.println(l);
            }
        });
    }

    @Test
    void testGetUserByTags(){
        List<String> tags = Arrays.asList("java","python");
        List<UserVO> userByTags = userService.getUserByTags(tags);
        Assertions.assertNotNull(userByTags);
    }
}