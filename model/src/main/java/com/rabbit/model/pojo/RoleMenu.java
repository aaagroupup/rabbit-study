package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author rabbit
 * @since 2022-09-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
@ApiModel(value = "RoleMenu对象", description = "角色菜单关系表")
public class RoleMenu implements Serializable {

      private static final long serialVersionUID = 1L;

      @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.ASSIGN_ID)
      private Long id;

      @ApiModelProperty("角色id")
      private Integer roleId;

      @ApiModelProperty("菜单id")
      private Integer menuId;

        
}
