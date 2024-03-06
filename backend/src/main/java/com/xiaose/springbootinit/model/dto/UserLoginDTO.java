package com.xiaose.springbootinit.model.dto;

import lombok.Data;

/**
 * @description：
 * @author：Administrator
 * @date：2023/12/30 10:38
 */

@Data
public class UserLoginDTO {
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

}
