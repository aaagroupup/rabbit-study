package com.rabbit.model.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseQueryVo {

    @ApiModelProperty("课程讲师ID")
    private Long teacherId;

    @ApiModelProperty("课程专业ID")

    private Long subjectId;
    @ApiModelProperty("课程专业父级ID")

    private Long subjectPid;
    @ApiModelProperty("课程标题")
    private String title;
}
