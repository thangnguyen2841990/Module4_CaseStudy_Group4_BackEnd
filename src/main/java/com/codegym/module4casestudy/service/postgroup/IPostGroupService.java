package com.codegym.module4casestudy.service.postgroup;

import com.codegym.module4casestudy.model.entity.PostGroup;
import com.codegym.module4casestudy.service.IGeneralService;

import java.util.List;

public interface IPostGroupService extends IGeneralService<PostGroup> {
    Iterable<PostGroup> findAll();

    void deletePostGroup(Long id);

    List<PostGroup> findByGroup(Long id);

}
