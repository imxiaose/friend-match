package com.xiaose.springbootinit.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaose.springbootinit.common.ErrorCode;
import com.xiaose.springbootinit.constant.UserConstant;
import com.xiaose.springbootinit.exception.BusinessException;
import com.xiaose.springbootinit.mapper.UserMapper;
import com.xiaose.springbootinit.model.domain.User;
import com.xiaose.springbootinit.model.dto.UserRegisterDTO;
import com.xiaose.springbootinit.model.vo.UserVO;
import com.xiaose.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xiaose.springbootinit.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author xiaose
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-12-29 23:24:20
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(UserRegisterDTO userDTO) {
        String checkPassword = userDTO.getCheckPassword();
        String userPassword = userDTO.getUserPassword();
        String userAccount = userDTO.getUserAccount();
        if (StringUtils.isAnyBlank(checkPassword, userPassword, userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "缺少参数");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "账户名过短");
        }
        if (checkPassword.length() < 8 || userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "密码过短");
        }
        // 账户不包含特殊字符
        String vaildPaten = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]+";
        Matcher matcher = Pattern.compile(vaildPaten).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名不能含有特殊字符");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不相同");
        }
        // 账户重复
        QueryWrapper<User> queryWrap = new QueryWrapper<>();
        queryWrap.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrap);
        if (count > 0) {
            throw new BusinessException(ErrorCode.VERIFY_ACCOUNT, "用户重复");
        }

        // 加密
        User user = getDigestUser(userAccount, userPassword);
        boolean result = this.save(user);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册用户失败");
        }
        return user.getId();
    }

    @Override
    public void userVaile(User user, boolean isFirst) {
        // 校验逻辑
    }

    @Override
    public UserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userPassword, userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "缺少参数");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "账户名过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "密码过短");
        }
        // 账户不包含特殊字符
        String vaildPaten = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]+";
        Matcher matcher = Pattern.compile(vaildPaten).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名不能含有特殊字符");
        }
        // 加密
        User digestUser = getDigestUser(userAccount, userPassword);
        // 查询用户是否存在
        QueryWrapper<User> wrap = new QueryWrapper<>();
        wrap.eq("userAccount",digestUser.getUserAccount());
        wrap.eq("userPassword",digestUser.getUserPassword());
        User user = userMapper.selectOne(wrap);
        // 用户不存在
        if (ObjectUtils.isEmpty(user)){
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }

        // 脱敏
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        // System.out.println(userVO);
        // 记录用户登录态
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE,userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserByTags(List<String> tagList){
        if (CollectionUtil.isEmpty(tagList)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"tagList 不能为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 拼接and查询
        for (String tag : tagList) {
            queryWrapper = queryWrapper.like("tags",tag);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : users) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        }
        return userVOList;

    }

    @Override
    public Integer updateUser(User user, User loginUser) {
        Long userId = user.getId();
        if (userId == null || userId<=0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        if (!isAdmin(loginUser) && !userId.equals(loginUser.getId())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        User oldUser = userMapper.selectById(userId);
        if (oldUser == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

        return userMapper.updateById(user);
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object UserVO = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (null == UserVO){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(UserVO,user) ;
        return user;
    }

    /**
     * 获取一个密码加盐后的用户
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return 密码加盐后的用户
     */
    private User getDigestUser(String userAccount, String userPassword) {
        String newPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        return user;
    }

    @Override
    public boolean isAdmin(HttpServletRequest request){
        UserVO userSeeion = (UserVO) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (ObjectUtils.isEmpty(userSeeion) || !UserConstant.ADMIN_ROLE.equals(userSeeion.getUserRole())){
            return false;
        }
        return true;
    }

    @Override
    public boolean isAdmin(User loginUser){

        if (ObjectUtils.isEmpty(loginUser) || !UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole())){
            return false;
        }
        return true;
    }




}




