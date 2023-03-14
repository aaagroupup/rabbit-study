package com.rabbit.studyweb.vod.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.Video;
import com.rabbit.studyweb.exception.StudyWebException;
import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.vod.service.IVideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2023-02-07
 */

@RestController
@RequestMapping("/vod/video")
public class VideoController {

    @Autowired
    private IVideoService videoService;

    /**
     * 保存视频
     * @param video
     * @return
     */
    @PostMapping("saveVideo")
    @CacheEvict(value = {"menu-banner","front-home-course"})
    public R saveVideo(@RequestBody Video video){
        if(StrUtil.isEmpty(video.getTitle())){
            return R.error("请输入视频标题");
        }
        if(StrUtil.isEmpty(video.getVideoOriginalName())){
            return R.error("请选择上传的视频");
        }
        boolean flag = videoService.save(video);
        if(!flag){
            R.error("添加视频失败!!!");
        }
        return R.success("视频添加成功");
    }

    /**
     * 查询当前课程是否有视频
     * @param courseId
     * @return
     */
    @GetMapping("getVideo")
    public R getCourse(@RequestParam("courseId") Long courseId){
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getCourseId,courseId);
        Video video = null;
        try {
            //此处可能查询出多条记录
            video = videoService.getOne(wrapper);
        } catch (Exception e) {
            throw new StudyWebException(400,"查询出了多条记录");
        }
        if(video!=null){
            return R.success("查询出了视频记录");
        }else{
            return R.error("没有查询出视频记录");
        }
    }

    /**
     * 更新课程的视频,并删除云端的视频
     * @param video
     * @return
     */
    @PutMapping("/updateVideo")
    public R updateVideoAndRemove(@RequestBody Video video){
        boolean flag = videoService.updateVideoAndRemove(video);
        if(!flag){
            return R.error("更新失败!!!");
        }
        return R.success("更新成功");
    }

    //根据id查询当前课程视频和封面
    @GetMapping("/getVideoCoverAndUrl/{id}")
    public R getVideoCoverAndUrl(@PathVariable Long id){
        Map<String,String> videoVo = videoService.getVideoUrl(id);
        return R.success(videoVo);
    }

    //根据id查询 远程调用
    @ApiOperation("先根据课程id查询出视频,之后删除视频")
    @DeleteMapping("getVideoAndRemove")
    public void getVideoAndRemove(@RequestParam Long id){
        videoService.getVideoAndRemove(id);
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("delBatchVideo")
    public void delBatchVideo(@RequestBody List<Integer> ids){
        videoService.delBatchVideo(ids);
    }

}

