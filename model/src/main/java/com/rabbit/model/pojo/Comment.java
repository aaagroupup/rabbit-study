package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author rabbit
 * @since 2022-10-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_comment")
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("评论人id")
    private Integer userId;

    @ApiModelProperty("评论时间")
    private String time;

    @ApiModelProperty("父id")
    private Integer pid;

    @TableField(exist = false)
    private String pNickname;//父节点的昵称

    @TableField(exist = false)
    private Integer pUserId;//父节点的用户id

    @ApiModelProperty("最上级评论id")
    private Integer originId;

    @ApiModelProperty("关联文章的id")
    private Integer articleId;

    private Long courseId;

    @TableField(exist = false)
    private String nickname;

    @TableField(exist = false)
    private List<Comment> children;


}
