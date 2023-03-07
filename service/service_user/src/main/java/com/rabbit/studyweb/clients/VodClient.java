package com.rabbit.studyweb.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("service-vod")
public interface VodClient {

    @DeleteMapping("/vod/video/getVideoAndRemove")
    void getVideoAndRemove(Long id);

    @DeleteMapping("/vod/video/delBatchVideo")
    void delBatchVideo(@RequestBody List<Integer> ids);
}
