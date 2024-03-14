package com.xiaose.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaose.springbootinit.common.BaseResponse;
import com.xiaose.springbootinit.common.ErrorCode;
import com.xiaose.springbootinit.common.ResultUtils;
import com.xiaose.springbootinit.constant.UserConstant;
import com.xiaose.springbootinit.exception.BusinessException;
import com.xiaose.springbootinit.model.domain.User;
import com.xiaose.springbootinit.model.dto.UserLoginDTO;
import com.xiaose.springbootinit.model.dto.UserRegisterDTO;
import com.xiaose.springbootinit.model.vo.UserVO;
import com.xiaose.springbootinit.service.UserService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.RequestUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.xiaose.springbootinit.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @description：
 * @author：Administrator
 * @date：2023/12/30 10:22
 */

@RestControllerAdvice
@Slf4j
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterDTO userDTO){
        if (ObjectUtils.isEmpty(userDTO)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"没有获取到用户");
        }
        if (StringUtils.isAnyBlank(
                userDTO.getCheckPassword(),
                userDTO.getUserPassword(),
                userDTO.getUserAccount())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"缺少必要参数");
        }
        return ResultUtils.success(userService.userRegister(userDTO));
    }

    @PostMapping("login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginDTO userDTO, HttpServletRequest request){
        if (ObjectUtils.isEmpty(userDTO)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"没有获取到用户");
        }
        if (StringUtils.isAnyBlank(
                userDTO.getUserPassword(),
                userDTO.getUserAccount())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"缺少必要参数");
        }
        return ResultUtils.success(userService.userLogin(userDTO.getUserAccount(), userDTO.getUserPassword(), request));
    }

    @GetMapping("search")
    public List<UserVO> searchUsers(String username, HttpServletRequest request){
        // 鉴权,仅管理员可以查询
        if (!userService.isAdmin(request)){
            return new ArrayList<>();
        }

        QueryWrapper<User> queryWrap = new QueryWrapper<>();
        queryWrap.like(StringUtils.isNotBlank(username),"username", username);
        List<User> list = userService.list(queryWrap);
        List<UserVO> users = new ArrayList<>();
        for (User user : list) {
            UserVO userVo = new UserVO();
            BeanUtils.copyProperties(user, userVo);
            users.add(userVo);
        }
        return users;
    }

    @PostMapping("delete")
    public boolean deleteUser(@RequestBody Long id, HttpServletRequest request){
        // 鉴权,仅管理员可以查询
        if (!userService.isAdmin(request)){
            return false;
        }
        if (null == id|| id < 0){
            return false;
        }
        return userService.removeById(id);
    }

    @GetMapping("/getCurrentUser")
    public BaseResponse<UserVO> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO currentUser = (UserVO)session.getAttribute(USER_LOGIN_STATE);
        log.info("currentUser : {}",currentUser);
        if (ObjectUtils.isEmpty(currentUser)){
            return null;
        }
        long userId = currentUser.getId();
        // todo： 后续用户新增特殊状态（例如被ban），应该修改该语句
        User user = userService.getById(userId);
        if (ObjectUtils.isEmpty(user)){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return ResultUtils.success(userVO);
    }

    @GetMapping("/search/tags")
    public BaseResponse<List<UserVO>> searchUserByTags(@RequestParam(required = false) List<String> tagNameList){
        List<UserVO> userByTags = userService.getUserByTags(tagNameList);
        return ResultUtils.success(userByTags);
    }

    @PostMapping("update")
    public BaseResponse<Integer> updateUser(@RequestBody User user, HttpServletRequest request){
        // 1. 参数是否为空
        if (null == user){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);

        return ResultUtils.success(userService.updateUser(user,loginUser));
    }

 
}
