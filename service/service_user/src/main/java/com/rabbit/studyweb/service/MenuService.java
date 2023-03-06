package com.rabbit.studyweb.service;



import com.rabbit.model.pojo.MainMenu;
import com.rabbit.model.pojo.dto.SubMenuDTO;

import java.util.List;

public interface MenuService {
    List<SubMenuDTO> getAllMenu(String query, int currentPage, int pageSize);

    boolean addMenu(SubMenuDTO menu);

    int getMenuCounts(String query);

    boolean deleteMenu(Integer id);

    SubMenuDTO getUpdateMenu(Integer id);

    boolean updateMenu(SubMenuDTO menu);

    List<MainMenu> getMenus();

    List<MainMenu> getFPath();
}
