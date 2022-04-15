package com.codegym.module4casestudy.service.imagePostGroup;

import com.codegym.module4casestudy.model.entity.ImagePostGroup;
import com.codegym.module4casestudy.service.IGeneralService;

import java.util.Optional;

public interface IImagePostGroupService extends IGeneralService<ImagePostGroup> {
    Iterable<ImagePostGroup> findAll();

    ImagePostGroup[] listImage(Long postGroup);

    Optional<ImagePostGroup> findByPostGroupId(Long postGroupId);

    String findImageById(Long imageId);
}
