package com.codegym.module4casestudy.service.post;

import com.codegym.module4casestudy.model.entity.PostUser;
import com.codegym.module4casestudy.service.IGeneralService;

import java.util.List;

public interface IPostService extends IGeneralService<PostUser> {
    Iterable<PostUser> findAll();

    void deletePost(Long postUserId);
}
