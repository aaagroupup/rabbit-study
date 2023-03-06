package com.rabbit.model.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDTO implements Serializable {
    private String username;
    private String telephone;
    private String password;
    private String newPassword;
}
