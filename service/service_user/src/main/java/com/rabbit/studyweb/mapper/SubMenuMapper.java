package com.rabbit.studyweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.model.pojo.SubMenu;
import com.rabbit.model.pojo.dto.SubMenuDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubMenuMapper extends BaseMapper<SubMenu> {

    @Select("select * from submenu where pid=#{pid}")
    List<SubMenu> getSubmenu(int pid);

    @Select("select submenu.*,mainmenu.path fPath from submenu,mainmenu where submenu.title like concat('%',replace(#{title},' ',''),'%') and submenu.pid=mainmenu.id limit #{currentPage},#{pageSize}")
    List<SubMenuDTO> getAllSubMenu(@Param("title") String title, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    @Select("select count(*) from submenu where title like concat('%',#{title},'%') ")
    int getSubMenuCounts(@Param("title") String title);


    @Insert("insert into submenu(title,path,pid,icon,page_path) " +
            "value(#{title},#{path},${pid},#{icon},#{pagePath})")
    boolean addSubMenu(SubMenu submenu);

    @Delete("delete from submenu where id=#{id}")
    boolean deleteSubMenu(Integer id);

    @Select("select submenu.*,mainmenu.path fPath from submenu,mainmenu where submenu.pid=mainmenu.id and submenu.id=#{id}")
    SubMenuDTO getUpdateSubMenu(Integer id);

    @Update("update submenu set title=#{title},path=#{path},pid=#{pid},icon=#{icon},page_path=#{pagePath} where id=#{id}")
    boolean updateSubMenu(SubMenuDTO submenu);
}
