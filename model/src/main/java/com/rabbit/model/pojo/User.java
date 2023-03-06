package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户实体
 */
@Data
@TableName("sys_user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String username;//用户名
    private String password;//密码
    private String email;//邮箱
    private String role;//角色
    private boolean state;//状态
    private String telephone;//电话
    private String nickname;//昵称
    private String avatarUrl;//头像

    @TableField(exist = false)
    private Integer count;//条数
}
