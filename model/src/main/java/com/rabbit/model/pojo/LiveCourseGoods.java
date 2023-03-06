package com.rabbit.model.pojo;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */
@TableName("live_course_goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LiveCourseGoods对象", description = "")
public class LiveCourseGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long liveCourseId;

    @TableField("goods_id")
    private Long goodsId;

    @TableField("name")
    private String title;

    @TableField("img")
    private String cover;

    private BigDecimal price;


}
