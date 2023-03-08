package com.rabbit.studyweb.service;

import com.rabbit.model.pojo.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbit.model.pojo.dto.SubjectDTO;
import com.rabbit.model.pojo.vo.CourseQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-05
 */
public interface ICourseService extends IService<Course> {

    Map<String,Object> findPageCourse(int currentPage2, int pageSize, CourseQueryVo courseQueryVo);

    boolean updateStatus(Long id, Boolean status);

    List<Course> findList();

    List<Course> getCourseByIds(List<Long> ids);

    Course getCourseDetail(Long id);

    List<Course> getCourseListByIds(List<Long> courseIds);

    List<Course> getUserFreeCourseList();

    List<Course> getCourseQuery(String searchText,List<Long> courseIds);

    List<SubjectDTO> getSubjectAndCourse();

    boolean addFreeCourse(Integer userId, Long id);
}
