package com.rabbit.studyweb.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("service-vod")
public interface VodClient {

    @GetMapping("/vod/video/getVideoAndRemove")
    void getVideoAndRemove(Long id);

    @GetMapping("/vod/video/delBatchVideo")
    void delBatchVideo(List<Integer> ids);
}
