package com.rabbit.studyweb.service;

import com.rabbit.model.pojo.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbit.model.pojo.dto.HomeMenusDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-05
 */
public interface ISubjectService extends IService<Subject> {

    List<Subject> selectSubjectList(Long id);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);

    List<Subject> getSubjectList();

    List<HomeMenusDTO> getMenus();
}
