package com.xiaose.springbootinit.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description：
 * @author：Administrator
 * @date：2023/12/30 10:11
 */

@Data
public class UserVO implements Serializable {
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;


    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 用户角色
     */
    private Integer userRole;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 标签
     */
    private String tags;

    /**
     * 是否有效
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;
}
