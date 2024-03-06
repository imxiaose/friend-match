package com.xiaose.springbootinit.constant;

/**
 * 用户常量
 *
 * @author <a href="https://github.com/lixiaose">程序员鱼皮</a>
 * @from <a href="https://xiaose.icu">编程导航知识星球</a>
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * 默认角色
     */
    Integer DEFAULT_ROLE = 0;

    /**
     * 管理员角色
     */
    Integer ADMIN_ROLE = 1;

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    String SALT = "xiaose";
}
