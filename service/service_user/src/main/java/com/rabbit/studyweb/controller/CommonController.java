package com.rabbit.studyweb.controller;

import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
public class CommonController {


    @Autowired
    private CommonService commonService;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        String fileName = commonService.upload(file);

        return R.success(fileName);
    }

}
