package com.rabbit.model.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {
    @ApiModelProperty("订单号")
    private String number;

    @ApiModelProperty("电话号")
    private String telephone;
    @ApiModelProperty("订单开始")
    private String startTime;
    @ApiModelProperty("订单结束")
    private String endTime;

    @ApiModelProperty("订单状态")
    private String status;

}
