package com.rabbit.model.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveCourseGoodsView {

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("图片")
    private String img;
    
    @ApiModelProperty("商品价格")
    private String price;

    @ApiModelProperty("商品原价")
    private String originalPrice;

    @ApiModelProperty("商品标签")
    private String tab;

    @ApiModelProperty("商品链接")
    private String url;

    @ApiModelProperty("商品状态:0下架,1上架,2推荐")
    private String putaway;

    @ApiModelProperty("购买模式")
    private String pay;

    @ApiModelProperty("商品二维码")
    private String qrcode;
    
}