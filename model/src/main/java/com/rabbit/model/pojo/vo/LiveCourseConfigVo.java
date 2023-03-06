package com.rabbit.model.pojo.vo;

import com.rabbit.model.pojo.LiveCourseConfig;
import com.rabbit.model.pojo.LiveCourseGoods;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveCourseConfigVo extends LiveCourseConfig {

    @ApiModelProperty("商品列表")
    private List<LiveCourseGoods> liveCourseGoodsList;
}
