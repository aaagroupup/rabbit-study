package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.studyweb.mapper.IconMapper;
import com.rabbit.model.pojo.Icon;
import com.rabbit.studyweb.service.IconService;
import org.springframework.stereotype.Service;

@Service
public class IconServiceImpl extends ServiceImpl<IconMapper, Icon> implements IconService  {
}
