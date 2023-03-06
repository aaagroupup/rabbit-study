package com.rabbit.studyweb.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.rabbit.studyweb.service.CommonService;
import com.rabbit.studyweb.utils.OSSUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public String upload(MultipartFile file) {
        String endpoint = OSSUtil.END_POINT;

        String accessKeyId =OSSUtil.ACCESS_KEY_ID;
        String accessKeySecret =OSSUtil.ACCESS_KEY_SECRET;

        String bucketName =OSSUtil.BUCKET_NAME;

        //文件名称,去重
        String objectName = UUID.randomUUID().toString().replaceAll("-","")+
                file.getOriginalFilename();
        //对文件分组，根据日期分组
//        String dateTime=new DateTime().toString("yyyy/MM/dd");
//        objectName=dateTime+"/"+objectName;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata=new ObjectMetadata();
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName,inputStream,objectMetadata);

            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");

            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            //返回文件上传路径
            String url="https://"+bucketName+".cos."+endpoint+".aliyuncs.com/"+objectName;
            return objectName;
        } catch (Exception oe) {
            oe.printStackTrace();
            return null;
        }

    }
}
