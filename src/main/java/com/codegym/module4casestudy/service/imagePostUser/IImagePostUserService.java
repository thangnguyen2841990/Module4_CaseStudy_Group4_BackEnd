package com.codegym.module4casestudy.service.imagePostUser;

import com.codegym.module4casestudy.model.entity.ImagePostUser;
import com.codegym.module4casestudy.service.IGeneralService;

public interface IImagePostUserService extends IGeneralService<ImagePostUser> {

    Iterable<ImagePostUser> findAll();
}
