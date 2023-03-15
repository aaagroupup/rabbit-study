package com.rabbit.studyweb.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@FeignClient("service-vod")
public interface VodClient {

    @DeleteMapping("/vod/video/getVideoAndRemove")
    void getVideoAndRemove(@RequestParam Long id);

    @DeleteMapping("/vod/video/delBatchVideo")
    void delBatchVideo(@RequestBody List<Integer> ids);
}
