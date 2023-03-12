package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbit.model.pojo.Course;
import com.rabbit.model.pojo.Subject;
import com.rabbit.model.pojo.Teacher;
import com.rabbit.model.pojo.dto.HomeMenusDTO;
import com.rabbit.model.pojo.dto.SubjectDTO;
import com.rabbit.model.pojo.vo.CourseQueryVo;
import com.rabbit.studyweb.mapper.CourseMapper;
import com.rabbit.model.pojo.UserCourse;
import com.rabbit.studyweb.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.studyweb.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-05
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private IUserCourseService userCourseService;

    @Override
    public Map<String, Object> findPageCourse(int currentPage,int pageSize, CourseQueryVo courseQueryVo) {

        //获取条件值
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();
        Long subjectPid = courseQueryVo.getSubjectPid();
        Long teacherId = courseQueryVo.getTeacherId();
        //判断条件是否为空，封装条件
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(title)){
            wrapper.like(Course::getTitle,title);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq(Course::getSubjectId,subjectId);
        }
        if(!StringUtils.isEmpty(subjectPid)){
            wrapper.eq(Course::getSubjectPid,subjectPid);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq(Course::getTeacherId,teacherId);
        }
        //调用方法实现条件分页查询
        IPage<Course> page=new Page<>(currentPage,pageSize);
        IPage<Course> courseIPage = courseMapper.selectPage(page, wrapper);
        List<Course> courseList = courseIPage.getRecords();
        int total =(int) this.count(wrapper);
        //将数据中的id转为对应的名称
        courseList.forEach(this::getNameById);

        //封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("courseList",courseList);
        return map;
    }

    @Override
    public boolean updateStatus(Long id, Boolean status) {
        return courseMapper.updateState(id,status);
    }

    @Override
    public List<Course> findList() {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::isStatus,true);
        List<Course> courseList = baseMapper.selectList(wrapper);
        courseList.forEach(this::getNameById);
        return courseList;
    }

    @Override
    public List<Course> getCourseByIds(List<Long> ids) {
        if(!ids.isEmpty()){
            List<Course> courseList = baseMapper.selectBatchIds(ids);
            courseList.forEach(this::getNameById);
            return courseList;
        }else{
            return null;
        }
    }

    @Override
    public Course getCourseDetail(Long id) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getId,id)
                .eq(Course::isStatus,true);
        Course one = baseMapper.selectOne(wrapper);
        Course course = getNameById(one);
        BeanUtils.copyProperties(one,course);
        return course;
    }

    @Override
    public List<Course> getCourseListByIds(List<Long> courseIds) {
        List<Course> courseList = this.listByIds(courseIds);
        getTeacherName(courseList);
        return courseList;
    }

    @Override
    public List<Course> getUserFreeCourseList() {
        //获得免费课程id
        Integer id = TokenUtils.getCurrentUser().getId();
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId, id);
        List<UserCourse> userCourseList = userCourseService.list(wrapper);
        List<Long> courseIds = userCourseList.stream().map(UserCourse::getCourseId).collect(Collectors.toList());
        List<Course> courseList = baseMapper.selectBatchIds(courseIds);
        getTeacherName(courseList);
        return courseList;
    }

    //条件查询课程
    @Override
    public List<Course> getCourseQuery(String searchText,List<Long> courseIds) {
        //判断条件是否为空
        if(searchText==null || searchText.length()==0){
            return this.getCourseListByIds(courseIds);
        }else{
            List<Course> courseList = baseMapper.selectCourseQuery(searchText, courseIds);
            getTeacherName(courseList);
            return courseList;
        }
    }

    //获得学科下所有课程
    @Override
    public List<SubjectDTO> getSubjectAndCourse() {
        ArrayList<SubjectDTO> subjectDTOList = new ArrayList<>();
        SubjectDTO subjectDTO;
        List<Subject> subjectList = subjectService.list();
        for (Subject subject : subjectList) {
            subjectDTO = new SubjectDTO();
            subjectDTO.setId(subject.getId());
            subjectDTO.setTitle(subject.getTitle());
            LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Course::getSubjectPid,subject.getId())
                    .eq(Course::isStatus,1);
            List<Course> courseList = this.list(wrapper);
            if(!courseList.isEmpty()){
                getTeacherName(courseList);
                subjectDTO.setCourseList(courseList);
                subjectDTOList.add(subjectDTO);
            }
        }
        return subjectDTOList;
    }

    @Override
    public boolean addFreeCourse(Integer userId, Long id) {
        UserCourse userCourse = new UserCourse();
        userCourse.setUserId(userId);
        userCourse.setCourseId(id);
        //给该课程销售数量+1
        Course course = baseMapper.selectById(id);
        course.setBuyCount(course.getBuyCount()+1);
        baseMapper.updateById(course);

        return userCourseService.save(userCourse);
    }

    //查询销售前三的课程
    @Override
    public List<Course> findTopThreeCourse() {
        return baseMapper.findTopThree();
    }

    //主页面左侧导航栏
    @Override
    public List<HomeMenusDTO> getMenus() {
        List<HomeMenusDTO> homeMenusDTOS=new ArrayList<>();
        LambdaQueryWrapper<Subject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Subject::getPid,0);
        List<Subject> subjectList = subjectService.list(queryWrapper);
        HomeMenusDTO homeMenusDTO;
        //只展示前十条
        for (int i = 0, length = Math.min(subjectList.size(), 10); i < length; i++) {
            Subject subject = subjectList.get(i);
            homeMenusDTO = new HomeMenusDTO();
            LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Course::getSubjectPid, subject.getId())
                    .eq(Course::isStatus, true);
            List<Course> courseList = baseMapper.selectList(wrapper);
            if (!courseList.isEmpty()) {
                homeMenusDTO.setId(subject.getId());
                homeMenusDTO.setTitle(subject.getTitle());
                //取课程前五条数据
                homeMenusDTO.setCourseList(courseList.size() > 5 ? courseList.subList(0, 5) : courseList);
                homeMenusDTOS.add(homeMenusDTO);
            }
        }
        return homeMenusDTOS;
    }

    //转换讲师姓名
    private void getTeacherName(List<Course> courseList) {
        courseList.forEach(course -> course.getParam().put("teacherName", teacherService.getById(course.getTeacherId()).getName()));
    }


    //将数据中的id转为对应的名称
    private Course getNameById(Course course) {

        //讲师名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(teacher!=null){
            String name=teacher.getName();
            course.getParam().put("teacherName",name);
        }

        //课程分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectPid());
        if(subjectOne!=null){
            course.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }

        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if(subjectTwo!=null){
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }
        return course;

    }

}
