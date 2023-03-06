package com.rabbit.studyweb.vod.controller;

import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/vod/vod")
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频
    @PostMapping("/upload")
    public R upload(MultipartFile file){
        String videoId= vodService.uploadVideo(file);

        return R.success(videoId);
    }

    //删除视频
    @DeleteMapping("/delete/{id}")
    public R deleteVideo(@PathVariable String id){
        vodService.deleteVideo(id);
        return R.success("删除成功");
    }

    /**
     * 视频id获取视频播放地址
     */
    @GetMapping("getVideoPlayInfo/{id}")
    @ApiOperation(value = "获取视频播放凭证", notes = "获取视频播放凭证")
    public R getVideoPlayInfo(@PathVariable String id) {
        Map<String,String> map = vodService.getVideoPlayInfo(id);
        return R.success(map);
    }

}
