package com.rabbit.model.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVisitorVo {

    @ApiModelProperty("进入时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String joinTime;

    @ApiModelProperty("用户个数")
    private Integer userCount;
}
