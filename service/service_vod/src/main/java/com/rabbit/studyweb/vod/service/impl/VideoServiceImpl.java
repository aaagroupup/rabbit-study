package com.rabbit.studyweb.vod.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.Video;
import com.rabbit.studyweb.vod.mapper.VideoMapper;
import com.rabbit.studyweb.vod.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.studyweb.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-07
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VodService vodService;

    //查询出视频后删除
    @Override
    public void getVideoAndRemove(Long id) {
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getCourseId,id);
        Video video = videoMapper.selectOne(wrapper);
        if (video!=null){
            if(!StrUtil.isEmpty(video.getVideoSourceId())){
                //删除云端视频
                vodService.deleteVideo(video.getVideoSourceId());
            }
            videoMapper.deleteById(video);
        }
    }

    @Override
    public boolean updateVideoAndRemove(Video video) {
        //先根据courseId查询出记录，再删除云端视频
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getCourseId,video.getCourseId());
        Video one = videoMapper.selectOne(wrapper);
        String videoSourceId = one.getVideoSourceId();
        if(!StrUtil.isEmpty(videoSourceId)){
            //删除云端视频
            vodService.deleteVideo(videoSourceId);
        }
        //再更新数据库中的记录
        int num = videoMapper.update(video,null);
        return num>0;
    }

    //批量删除视频
    @Override
    public void delBatchVideo(List<Integer> ids) {
        //先根据课程数组找到所对应的视频id，再删除云端视频，之后删除数据库中的记录
        for (Integer id : ids) {
            LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Video::getCourseId,id);
            Video video = videoMapper.selectOne(wrapper);
            //如果要删除的课程存在视频，则删除云端视频和本地记录，否则不需要删除
            if(video!=null){
                vodService.deleteVideo(video.getVideoSourceId());
                videoMapper.deleteById(video);
            }
        }


    }

    //获得当前课程的视频
    @Override
    public Map<String,String> getVideoUrl(Long id) {

        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getCourseId,id);
        Video video = this.getOne(wrapper);
        Map<String, String> videoPlayInfo = vodService.getVideoPlayInfo(video.getVideoSourceId());
        Map<String, String> videoVo = new HashMap<>();
        videoVo.put("playUrl",videoPlayInfo.get("playUrl"));
        videoVo.put("coverUrl",videoPlayInfo.get("coverUrl"));
        return videoVo;
    }
}
