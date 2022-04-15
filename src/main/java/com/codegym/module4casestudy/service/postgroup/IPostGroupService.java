package com.codegym.module4casestudy.service.postgroup;

import com.codegym.module4casestudy.model.entity.PostGroup;
import com.codegym.module4casestudy.service.IGeneralService;

public interface IPostGroupService extends IGeneralService<PostGroup> {
    Iterable<PostGroup> findAll();

    void deletePostGroup(Long id);
}
