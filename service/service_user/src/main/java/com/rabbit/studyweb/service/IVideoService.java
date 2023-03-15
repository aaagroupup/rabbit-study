package com.rabbit.studyweb.service;

import com.rabbit.model.pojo.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-07
 */
public interface IVideoService extends IService<Video> {

    void getVideoAndRemove(Long id);

    boolean updateVideoAndRemove(Video video);

    void delBatchVideo(List<Integer> ids);

    Map<String,String> getVideoUrl(Long id);
}
