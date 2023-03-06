package com.rabbit.studyweb.service;

import com.rabbit.model.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-06
 */
public interface ITeacherService extends IService<Teacher> {

    List<Teacher> getTeacherList(String query, int currentPage, int pageSize);
}
