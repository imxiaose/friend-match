package com.xiaose.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaose.springbootinit.mapper.TagMapper;
import com.xiaose.springbootinit.model.domain.Tag;
import com.xiaose.springbootinit.service.TagService;

import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【tag(标签)】的数据库操作Service实现
* @createDate 2024-01-10 21:31:11
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService {

}




