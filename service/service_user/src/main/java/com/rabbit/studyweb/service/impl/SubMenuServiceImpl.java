package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.studyweb.mapper.SubMenuMapper;
import com.rabbit.model.pojo.SubMenu;
import com.rabbit.studyweb.service.SubMenuService;
import org.springframework.stereotype.Service;

@Service
public class SubMenuServiceImpl extends ServiceImpl<SubMenuMapper, SubMenu> implements SubMenuService {
}
