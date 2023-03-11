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

    @Value("${studyweb.path}")
    private String basePath;

    @Autowired
    private CommonService commonService;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        String fileName = commonService.upload(file);

        return R.success(fileName);
    }


    /**
     * 文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(basePath+name);

            //输出流，通过输出流将文件写回浏览器，在浏览器展示图片basePath
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len=0;
            byte[] bytes = new byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
        }
    }
}
