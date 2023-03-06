package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
@TableName("shopping_cart")
@ApiModel(value = "ShoppingCart对象", description = "")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty("商品名称")
    private String goodsname;

    @ApiModelProperty("商品图片")
    private String image;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("商品数量")
    private Integer number;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("金额")
    private Double amount;


}
