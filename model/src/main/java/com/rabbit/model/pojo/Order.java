package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author rabbit
 * @since 2022-10-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_order")
@ApiModel(value = "Order对象", description = "")
public class Order extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("订单号")
    private String number;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("下单时间")
    private String orderTime;

    @ApiModelProperty("总金额")
    private Double amount;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("电话")
    private String telephone;

    private String status;

    private String payTime;

    private String courseId;


}
