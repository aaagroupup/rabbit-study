package com.rabbit.studyweb.live.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbit.model.pojo.LiveCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbit.model.pojo.dto.LiveCourseDTO;
import com.rabbit.model.pojo.vo.LiveCourseConfigVo;
import com.rabbit.model.pojo.vo.LiveCourseVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */
public interface ILiveCourseService extends IService<LiveCourse> {

    IPage<LiveCourse> selectPage(Page<LiveCourse> liveCoursePage);

    void saveLive(LiveCourseVo liveCourseVo);

    void deleteLive(Long id);

    LiveCourseVo getLiveCourseVo(Long id);

    void updateLiveById(LiveCourseVo liveCourseVo);

    LiveCourseConfigVo getCourseConfig(Long courseId);

    void updateConfig(LiveCourseConfigVo liveCourseConfigVo);

    List<LiveCourseDTO> getLatelyList();

    JSONObject getAccessToken(Long id);
}
