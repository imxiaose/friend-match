package com.xiaose.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaose.springbootinit.model.domain.User;
import com.xiaose.springbootinit.model.dto.UserRegisterDTO;
import com.xiaose.springbootinit.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author xiaose
 * @description 用户服务
 * @createDate 2023-12-29 23:24:20
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param user 需要注册用户的信息
     * @return 新用户id
     */
    long userRegister(UserRegisterDTO user);

    /**
     * 用户校验
     *
     * @param user    用户信息
     * @param isFirst 是否第一次登陆
     */
    void userVaile(User user, boolean isFirst);

    /**
     * 用户登陆
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @return 脱敏后的信息
     */
    UserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 根据标签搜索用户
     * @param tagList 用户拥有的标签
     * @return 用户列表
     */
    List<UserVO> getUserByTags(List<String> tagList);

    /**
     * 更新用户信息
     *
     * @param user    需要更新的用户信息
     * @param loginUser 登录的用户信息
     * @return
     */
    Integer updateUser(User user, User loginUser);

    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    boolean isAdmin(HttpServletRequest request);

    boolean isAdmin(User loginUser);
}
