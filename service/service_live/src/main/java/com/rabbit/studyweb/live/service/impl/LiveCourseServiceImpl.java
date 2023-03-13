package com.rabbit.studyweb.live.service.impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbit.model.pojo.*;
import com.rabbit.model.pojo.dto.LiveCourseDTO;
import com.rabbit.model.pojo.vo.LiveCourseConfigVo;
import com.rabbit.model.pojo.vo.LiveCourseGoodsView;
import com.rabbit.model.pojo.vo.LiveCourseVo;
import com.rabbit.studyweb.exception.StudyWebException;
import com.rabbit.studyweb.live.clients.UserClient;
import com.rabbit.studyweb.live.mapper.LiveCourseMapper;
import com.rabbit.studyweb.live.mtcloud.CommonResult;
import com.rabbit.studyweb.live.mtcloud.MTCloud;
import com.rabbit.studyweb.live.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */
@Service
public class LiveCourseServiceImpl extends ServiceImpl<LiveCourseMapper, LiveCourse> implements ILiveCourseService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private MTCloud mtCloud;

    @Autowired
    private ILiveCourseDescriptionService liveCourseDescriptionService;

    @Autowired
    private ILiveCourseAccountService liveCourseAccountService;

    @Autowired
    private ILiveCourseConfigService liveCourseConfigService;

    @Autowired
    private ILiveCourseGoodsService liveCourseGoodsService;

    //@Autowired
    //private UserClient userClient;

    //直播课程列表
    @Override
    public IPage<LiveCourse> selectPage(Page<LiveCourse> liveCoursePage) {
        //分页查询
        Page<LiveCourse> page = baseMapper.selectPage(liveCoursePage, null);
        //获取课程讲师信息
        List<LiveCourse> liveCourseList = page.getRecords();
        for (LiveCourse liveCourse : liveCourseList) {
            //每个课程讲师id
            Long teacherId = liveCourse.getTeacherId();
            //根据讲师id查询讲师信息
            Teacher teacher = userClient.getTeacherInfo(teacherId);
            //进行封装
            if(teacher!=null){
                liveCourse.getParam().put("teacherName",teacher.getName());
                liveCourse.getParam().put("level",teacher.getLevel());
            }
            Long subjectId = liveCourse.getSubjectId();
            String subjectName = userClient.getSubjectName(subjectId);
            liveCourse.getParam().put("subjectName",subjectName);

        }
        return page;
    }

    //直播课程添加
    @Override
    public void saveLive(LiveCourseVo liveCourseVo) {
        //liveCourseVo -- >liveCourse
        LiveCourse liveCourse = new LiveCourse();
        BeanUtils.copyProperties(liveCourseVo,liveCourse);

        //获取讲师信息
        Teacher teacher =  userClient.getTeacherInfo(liveCourseVo.getTeacherId());

        //调用方法添加直播课程
        //创建map集合，封装直播课程其他参数
        HashMap<Object, Object> options = new HashMap<>();
        options.put("scenes",2);//直播类型 1.教育直播 2.生活直播 默认是1
        options.put("password",liveCourseVo.getPassword());

        try {
            String res = mtCloud.courseAdd(liveCourse.getCourseName(),
                    teacher.getId().toString(),
                    new DateTime(liveCourse.getStartTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    new DateTime(liveCourse.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    teacher.getName(),
                    teacher.getDescription(),
                    options);
            System.out.println("res=>"+res);
            //把创建之后的返回结果判断
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode())==MTCloud.CODE_SUCCESS){//成功
                //添加直播基本信息
                JSONObject data = commonResult.getData();
                Long course_id = data.getLong("course_id");//直播课程的id
                liveCourse.setCourseId(course_id);
                baseMapper.insert(liveCourse);

                //添加直播描述信息
                LiveCourseDescription liveCourseDescription = new LiveCourseDescription();
                liveCourseDescription.setLiveCourseId(liveCourse.getId());
                liveCourseDescription.setDescription(liveCourseVo.getDescription());
                liveCourseDescriptionService.save(liveCourseDescription);

                //添加直播账号信息
                LiveCourseAccount liveCourseAccount = new LiveCourseAccount();
                liveCourseAccount.setLiveCourseId(liveCourse.getId());
                liveCourseAccount.setZhuboAccount(data.getString("bid"));
                liveCourseAccount.setZhuboKey(data.getString("zhubo_key"));
                liveCourseAccount.setZhuboPassword(liveCourseVo.getPassword());
                liveCourseAccount.setAdminKey(data.getString("admin_key"));
                liveCourseAccount.setUserKey(data.getString("user_key"));
                liveCourseAccountService.save(liveCourseAccount);
            }else{
                System.out.println(commonResult.getmsg());
                throw new StudyWebException(400,"直播创建失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除直播课程
    @Override
    public void deleteLive(Long id) {
        //根据id查询出直播课程信息
        LiveCourse liveCourse = baseMapper.selectById(id);
        if(liveCourse!=null){
            //获取直播courseId
            Long courseId = liveCourse.getCourseId();

            try {
                //调用方法删除平台直播课程
                mtCloud.courseDelete(courseId.toString());

                //删除表中数据
                //删除liveCourse表中数据
                baseMapper.deleteById(id);

                //删除liveCourse_description表中数据
                LambdaQueryWrapper<LiveCourseDescription> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(LiveCourseDescription::getLiveCourseId,id);
                LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getOne(wrapper);
                liveCourseDescriptionService.removeById(liveCourseDescription);

                //删除liveCourse_Account表中数据
                LambdaQueryWrapper<LiveCourseAccount> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(LiveCourseAccount::getLiveCourseId,id);
                LiveCourseAccount liveCourseAccount = liveCourseAccountService.getOne(queryWrapper);
                liveCourseAccountService.removeById(liveCourseAccount);

            } catch (Exception e) {
                e.printStackTrace();
                throw new StudyWebException(400,"删除直播课程失败");
            }
        }
    }

    //根据id查询直播课程基本信息和描述信息
    @Override
    public LiveCourseVo getLiveCourseVo(Long id) {
        //获取直播课程基本信息
        LiveCourse liveCourse = baseMapper.selectById(id);
        //获取直播课程描述信息
        LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(id);
        //根据直播课程id获取主播密码
        String password = liveCourseAccountService.getZhuboPasswordByCourseId(id);
        //封装
        LiveCourseVo liveCourseVo = new LiveCourseVo();
        BeanUtils.copyProperties(liveCourse,liveCourseVo);
        liveCourseVo.setPassword(password);
        liveCourseVo.setDescription(liveCourseDescription.getDescription());
        return liveCourseVo;
    }

    //更新
    @Override
    public void updateLiveById(LiveCourseVo liveCourseVo) {
        //根据id获取直播课程基本信息
        LiveCourse liveCourse = baseMapper.selectById(liveCourseVo.getId());
        BeanUtils.copyProperties(liveCourseVo,liveCourse);
        //讲师
        Teacher teacher = userClient.getTeacherInfo(liveCourseVo.getTeacherId());

        HashMap<Object, Object> options = new HashMap<>();
        try {
            String res = mtCloud.courseUpdate(liveCourse.getCourseId().toString(),
                    teacher.getId().toString(),
                    liveCourse.getCourseName(),
                    new DateTime(liveCourse.getStartTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    new DateTime(liveCourse.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    teacher.getName(),
                    teacher.getDescription(),
                    options);
            //返回结果转换,判断是否成功
            CommonResult<JSONObject> commonResult = JSON.parseObject(res,CommonResult.class);
            if(Integer.parseInt(commonResult.getCode())==MTCloud.CODE_SUCCESS){
                JSONObject data = commonResult.getData();

                //更新直播课程基本信息
                liveCourse.setCourseId(data.getLong("course_id"));
                baseMapper.updateById(liveCourse);
                //更新密码
                LiveCourseAccount liveCourseAccount =
                        liveCourseAccountService.getLiveCourseAccountById(liveCourse.getId());
                liveCourseAccount.setZhuboPassword(liveCourseVo.getPassword());
                liveCourseAccountService.updateById(liveCourseAccount);
                //更新直播课程描述信息
                LiveCourseDescription liveCourseDescription =
                        liveCourseDescriptionService.getLiveCourseById(liveCourse.getId());
                liveCourseDescription.setDescription(liveCourseVo.getDescription());
                liveCourseDescriptionService.updateById(liveCourseDescription);

            }else{
                throw new StudyWebException(400,"修改直播课程失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取直播配置信息
    @Override
    public LiveCourseConfigVo getCourseConfig(Long liveCourseId) {
        //根据直播课程id查询配置信息
        LiveCourseConfig liveCourseConfig = liveCourseConfigService.getCourseConfigByCourseId(liveCourseId);
        //封装liveCourseConfigVO
        LiveCourseConfigVo liveCourseConfigVo = new LiveCourseConfigVo();
        if(liveCourseConfig!=null){
            //查询直播课程商品列表
            List<LiveCourseGoods> liveCourseGoodsList= liveCourseGoodsService.getGoodsListByCourseId(liveCourseId);
            //封装到liveCourseConfigVo里
            BeanUtils.copyProperties(liveCourseConfig,liveCourseConfigVo);
            //封装商品列表
            liveCourseConfigVo.setLiveCourseGoodsList(liveCourseGoodsList);
        }
        return liveCourseConfigVo;
    }

    //修改配置
    @Override
    public void updateConfig(LiveCourseConfigVo liveCourseConfigVo) {

        //1.修改直播配置表
        LiveCourseConfig liveCourseConfig = new LiveCourseConfig();
        BeanUtils.copyProperties(liveCourseConfigVo,liveCourseConfig);
        if(liveCourseConfigVo.getLiveCourseId()==null){
            liveCourseConfigService.save(liveCourseConfig);
        }else{
            liveCourseConfigService.update(liveCourseConfig,null);
        }
        //2.修改直播商品表
        //根据课程id先删除直播商品列表
        LambdaQueryWrapper<LiveCourseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseGoods::getLiveCourseId,liveCourseConfigVo.getLiveCourseId());
        liveCourseGoodsService.remove(wrapper);
        //添加商品列表
        if(!CollectionUtils.isEmpty(liveCourseConfigVo.getLiveCourseGoodsList())){
            liveCourseGoodsService.saveBatch(liveCourseConfigVo.getLiveCourseGoodsList());
        }

        //3.修改直播平台
        this.updateLiveConfig(liveCourseConfigVo);
    }

    //获取最近的直播
    @Override
    public List<LiveCourseDTO> getLatelyList() {
        List<LiveCourseDTO> liveCourseDTOList = baseMapper.getLatelyList();
        for (LiveCourseDTO liveCourseDTO : liveCourseDTOList) {
            //封装开始和结束时间
            liveCourseDTO.setStartTimeString(new DateTime(liveCourseDTO.getStartTime()).toString("yyyy年MM月dd HH:mm"));
            liveCourseDTO.setEndTimeString(new DateTime(liveCourseDTO.getEndTime()).toString("HH:mm"));
            //封装讲师
            Long teacherId = liveCourseDTO.getTeacherId();
            Teacher teacher =  userClient.getTeacherInfo(teacherId);
            liveCourseDTO.setTeacher(teacher);

            //封装直播状态
            liveCourseDTO.setLiveStatus(this.getLiveStatus(liveCourseDTO));
        }
        return liveCourseDTOList;
    }

    //获取用户access_token
    @Override
    public JSONObject getAccessToken(Long id) {
        //根据课程id获取直播课程信息
        LiveCourse liveCourse = baseMapper.selectById(id);
        //获取用户信息
        User user = userClient.getUserInfo();

        //封装需要的参数
        HashMap<Object, Object> options = new HashMap<>();
        /**
         *  进入一个课程
         *  @param    course_id      课程ID
         *  @param    uid            用户唯一ID
         *  @param    nickname       用户昵称
         *  @param    role           用户角色，枚举见:ROLE 定义
         *  @param       expire         有效期,默认:3600(单位:秒)
         *  @param     options        可选项，包括:gender:枚举见上面GENDER定义,avatar:头像地址
         */
        try {
            String res = mtCloud.courseAccess(liveCourse.getCourseId().toString(),
                    user.getId().toString(),
                    user.getNickname(),
                    MTCloud.ROLE_USER,
                    3600,
                    options);
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode())==MTCloud.CODE_SUCCESS){
                JSONObject data = commonResult.getData();
                System.out.println("access::"+data.getString("access_token"));
                return data;
            }else{
                throw new StudyWebException(400,"获取失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getLiveStatus(LiveCourse liveCourse) {
        //直播状态 0:未开始 1:直播中 2:直播结束
        int liveStatus=0;
        Date currentDate = new Date();
        if(liveCourse.getStartTime().compareTo(currentDate.toString()) > 0){
            liveStatus=0;
        }else if(liveCourse.getEndTime().compareTo(currentDate.toString())>0){
            liveStatus=1;
        }else{
            liveStatus=2;
        }
        return liveStatus;
    }

    private void updateLiveConfig(LiveCourseConfigVo liveCourseConfigVo) {
        System.err.println("liveCourseConfigVo=>"+liveCourseConfigVo.getLiveCourseId());
        LiveCourse liveCourse = baseMapper.selectById(liveCourseConfigVo.getLiveCourseId());
        //封装平台方法需要的参数

        //参数设置
        HashMap<Object, Object> options = new HashMap<>();
        //界面模式
        options.put("pageViewMode",liveCourseConfigVo.getPageViewMode());
        //观看人数开关
        JSONObject number = new JSONObject();
        number.put("enable",liveCourseConfigVo.getNumberEnable());
        options.put("number",number.toJSONString());
        //商店开启开关
        JSONObject store = new JSONObject();
        number.put("enable",liveCourseConfigVo.getStoreEnable());
        number.put("type",liveCourseConfigVo.getStoreType());
        options.put("store",store.toJSONString());
        //商城列表
        List<LiveCourseGoods> liveCourseGoodsList = liveCourseConfigVo.getLiveCourseGoodsList();
        if(!CollectionUtils.isEmpty(liveCourseGoodsList)){
            List<LiveCourseGoodsView> liveCourseGoodsViewList = new ArrayList<>();
            for (LiveCourseGoods liveCourseGoods : liveCourseGoodsList) {
                LiveCourseGoodsView liveCourseGoodsView=new LiveCourseGoodsView();
                BeanUtils.copyProperties(liveCourseGoods,liveCourseGoodsView);
            }
            JSONObject goodsListEdit = new JSONObject();
            goodsListEdit.put("status",0);
            options.put("goodsListEdit",goodsListEdit.toJSONString());
            options.put("goodsList", JSON.toJSONString(liveCourseGoodsViewList));
        }

        try {
            String res = mtCloud.courseUpdateConfig(liveCourse.getCourseId().toString(), options);
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode())!=MTCloud.CODE_SUCCESS){
                throw new StudyWebException(400,"修改配置信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
