package com.rabbit.studyweb.mapper;

import com.rabbit.model.pojo.MainMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("select * from mainmenu")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "path",column = "path"),
            @Result(property = "icon",column = "icon"),
            @Result(
                    property = "subMenuList",column = "id",
                    javaType = List.class,
                    many = @Many(select = "com.rabbit.studyweb.mapper.SubMenuMapper.getSubmenu")
            )
    })
    List<MainMenu> getMenus();

    @Select("select * from mainmenu")
    List<MainMenu> getMainMenus();

    @Select("select path from mainmenu")
    List<MainMenu> getFPath();
}
