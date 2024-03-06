package com.xiaose.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaose.springbootinit.model.domain.User;
import org.apache.ibatis.annotations.Mapper;


/**
* @author Administrator
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-12-29 23:24:20
* @Entity generator.domain.user
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




