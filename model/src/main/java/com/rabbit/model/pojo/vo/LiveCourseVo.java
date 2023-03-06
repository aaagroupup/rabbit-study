package com.rabbit.model.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveCourseVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("直播名称")
    private String courseName;

    @ApiModelProperty("直播开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty("直播结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    @ApiModelProperty("主播id")
    private Long teacherId;

    @ApiModelProperty("课程id")
    private Long subjectId;

    @ApiModelProperty("主播密码")
    private String password;

    @ApiModelProperty("课程简介")
    private String description;

    @ApiModelProperty("课程封面图片路径")
    private String cover;
}
