package com.rabbit.studyweb.service.impl;

import com.rabbit.model.pojo.VideoVisitor;
import com.rabbit.model.pojo.vo.VideoVisitorVo;
import com.rabbit.studyweb.mapper.VideoVisitorMapper;
import com.rabbit.studyweb.service.IVideoVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-09
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements IVideoVisitorService {

    @Autowired
    private VideoVisitorMapper videoVisitorMapper;

    //课程统计接口
    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        //调用mapper方法
        List<VideoVisitorVo> videoVisitorVoList= videoVisitorMapper.findCount(courseId,startDate,endDate);
        Map<String,Object> map=new HashMap<>();

        //封装数据  代表所有日期
        List<String> dateList = videoVisitorVoList.stream()
                .map(VideoVisitorVo::getJoinTime).collect(Collectors.toList());
        //封装数据  代表所有数量
        List<Integer> countList = videoVisitorVoList.stream()
                .map(VideoVisitorVo::getUserCount).collect(Collectors.toList());
        //如果日期为空，说明该课程没有人数观看，给出提示信息
        if(dateList.isEmpty()){
            map.put("code","400");
        }else{
            map.put("code","200");
        }
        map.put("xData",dateList);
        map.put("yData",countList);
        return map;
    }
}
