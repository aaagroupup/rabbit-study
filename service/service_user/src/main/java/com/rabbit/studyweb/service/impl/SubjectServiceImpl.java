package com.rabbit.studyweb.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.Subject;
import com.rabbit.model.pojo.vo.SubjectEeVo;
import com.rabbit.studyweb.exception.StudyWebException;
import com.rabbit.studyweb.listener.SubjectListener;
import com.rabbit.studyweb.mapper.SubjectMapper;
import com.rabbit.studyweb.service.ISubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-05
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {


    @Autowired
    private SubjectListener subjectListener;

    @Override
    public List<Subject> selectSubjectList(Long id) {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getPid,id);
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        for (Subject subject : subjectList) {
            Long subjectId = subject.getId();
            boolean isChild= this.isChildren(subjectId);
            subject.setHasChildren(isChild);
        }
        return subjectList;

    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            //设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("课程分类", "utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            //查询课程分类所有数据
            List<Subject> subjectList = baseMapper.selectList(null);

            List<SubjectEeVo> subjectEeVoList = new ArrayList<>();
            for (Subject subject : subjectList) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtils.copyProperties(subject,subjectEeVo);
                subjectEeVoList.add(subjectEeVo);
            }

            //EasyExcel写操作
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类").doWrite(subjectEeVoList);
        }catch (Exception e){
            throw new StudyWebException(400,"导出失败");
        }
    }

    //课程分类导入
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),SubjectEeVo.class,subjectListener).sheet().doRead();
        } catch (IOException e) {
            throw new StudyWebException(400,"导入失败");
        }
    }

    @Override
    public List<Subject> getSubjectList() {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getPid,0);
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        return subjectList;
    }

    //判断是否有下一层数据
    private boolean isChildren(Long subjectId) {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getPid,subjectId);
        Long count = baseMapper.selectCount(wrapper);
        return count>0;
    }
}
