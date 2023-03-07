package com.rabbit.studyweb.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.rabbit.studyweb.exception.StudyWebException;
import com.rabbit.studyweb.vod.service.VodService;
import com.rabbit.studyweb.vod.utils.OSSUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) {
        // 视频文件上传
        // 1.本地文件上传时，文件名称为上传文件绝对路径，如：/User/sample/文件名称.mp4 （必选）
        // 任何上传方式文件名必须包含扩展名
        String fileName = file.getOriginalFilename();
        // 视频标题（必选）
        assert fileName != null;
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String videoId = uploadVideo(OSSUtil.ACCESS_KEY_ID, OSSUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
        return videoId;
    }

    @Override
    public void deleteVideo(String videoId) {
        // 点播服务接入区域
        DefaultProfile profile = getDefaultProfile();

        //实例化要请求产品的client对象，profile可选
        IAcsClient client = new DefaultAcsClient(profile);
        //实例化一个请求对象，每个接口都会对应一个request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoId);

        try {
            //返回的response是一个DeleteVideoResponse的实例，与请求对象对应
            DeleteVideoResponse response = client.getAcsResponse(request);
            //System.out.println(new Gson().toJson(response));

        } catch (ClientException e) {
            throw  new StudyWebException(400,"视频删除失败");
        }
    }


    @Override
    public Map<String,String> getVideoPlayInfo(String id) {
        DefaultProfile profile = getDefaultProfile();

        IAcsClient client = new DefaultAcsClient(profile);

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(id);

        Map<String, String> map = new HashMap<>();
        try {
            GetPlayInfoResponse response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            String coverURL = response.getVideoBase().getCoverURL();
            String playURL ="";
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                playURL = playInfo.getPlayURL();
            }
            map.put("coverUrl",coverURL);
            map.put("playUrl",playURL);
            return  map;
            //System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return null;
    }

    //默认配置文件
    private DefaultProfile getDefaultProfile() {
        String regionId = "cn-shanghai";
        return DefaultProfile.getProfile(regionId,OSSUtil.ACCESS_KEY_ID,OSSUtil.ACCESS_KEY_SECRET);
    }

    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static String uploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName,InputStream inputStream) {

        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName,inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        if (response.isSuccess()) {
            //获得上传之后的视频ID
            String videoId = response.getVideoId();
            return videoId;
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            return null;
        }
    }
}
