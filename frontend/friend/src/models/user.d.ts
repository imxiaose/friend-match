/**
 * 用户类别
 */
export type UserType = {
    id: number;

    username?: string;

    /**
     * 账号
     */
    userAccount: string;

    /**
     * 头像
     */
    avatarUrl?:string;

    /**
     * 性别
     */
    gender?: number;

    /**
     * 电话
     */
    phone?: string;

    /**
     * 用户角色
     */
    userRole?: number;

    /**
     * 邮箱
     */
    email?: string;

    tags:string[];

    /**
     * 是否有效
     */
    userStatus: number;

    /**
     * 创建时间
     */
    createTime: Date;
};