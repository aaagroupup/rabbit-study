package com.rabbit.studyweb.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface VodService {

    String uploadVideo(MultipartFile file);

    void deleteVideo(String videoId);

    Map<String,String> getVideoPlayInfo(String id);
}
