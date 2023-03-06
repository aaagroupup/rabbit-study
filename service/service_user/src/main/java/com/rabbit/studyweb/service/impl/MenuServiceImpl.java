package com.rabbit.studyweb.service.impl;

import com.rabbit.studyweb.mapper.MenuMapper;
import com.rabbit.studyweb.mapper.SubMenuMapper;
import com.rabbit.model.pojo.MainMenu;
import com.rabbit.model.pojo.dto.SubMenuDTO;
import com.rabbit.studyweb.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {
    @Autowired
    private SubMenuMapper subMenuMapper;

    @Autowired
    private MenuMapper mainMenuMapper;

    @Override
    public List<SubMenuDTO> getAllMenu(String query, int currentPage, int pageSize) {
        return subMenuMapper.getAllSubMenu(query,currentPage,pageSize);
    }

    @Override
    public boolean addMenu(SubMenuDTO menu) {
        List<MainMenu> mainMenus = mainMenuMapper.getMainMenus();
        for (MainMenu mainMenu : mainMenus) {
            if (mainMenu.getPath().equals(menu.getFPath().replaceAll(" ",""))) {
                menu.setPid(mainMenu.getId());
            }else{
                menu.setPid(100);
            }
        }
        return subMenuMapper.addSubMenu(menu);
    }

    @Override
    public int getMenuCounts(String query) {
        return subMenuMapper.getSubMenuCounts(query);
    }

    @Override
    public boolean deleteMenu(Integer id) {
        return subMenuMapper.deleteSubMenu(id);
    }

    @Override
    public SubMenuDTO getUpdateMenu(Integer id) {
        return subMenuMapper.getUpdateSubMenu(id);
    }

    @Override
    public boolean updateMenu(SubMenuDTO menu) {
        List<MainMenu> mainMenus = mainMenuMapper.getMainMenus();
        for (MainMenu mainMenu : mainMenus) {
            if (mainMenu.getPath().equals(menu.getFPath().replaceAll(" ",""))) {
                menu.setPid(mainMenu.getId());
            }else{
                menu.setPid(100);
            }
        }
        return subMenuMapper.updateSubMenu(menu);
    }

    @Override
    public List<MainMenu> getMenus() {
        return mainMenuMapper.getMenus();
    }

    @Override
    public List<MainMenu> getFPath() {
        return mainMenuMapper.getFPath();
    }
}
