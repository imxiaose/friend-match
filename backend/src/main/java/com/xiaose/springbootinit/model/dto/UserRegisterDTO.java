package com.xiaose.springbootinit.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description：
 * @author：Administrator
 * @date：2023/12/30 0:00
 */
@Data
public class UserRegisterDTO implements Serializable {
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 较验密码
     */
    private String checkPassword;
}
