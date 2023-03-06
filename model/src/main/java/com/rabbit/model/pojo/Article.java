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
 * @since 2022-10-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
@ApiModel(value = "Article对象", description = "")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      @ApiModelProperty("标题")
      private String name;

      @ApiModelProperty("内容")
      private String content;

      @ApiModelProperty("发布人")
      private String nickname;

      @ApiModelProperty("发布时间")
      private String time;

      @ApiModelProperty("热度")
      private Integer hot;

                    
}
