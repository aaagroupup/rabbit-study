package com.rabbit.model.pojo.dto;

import com.rabbit.model.pojo.SubMenu;
import com.rabbit.model.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends User {
    private List<SubMenu> menus;
    private String token;

    private Integer code;
}
