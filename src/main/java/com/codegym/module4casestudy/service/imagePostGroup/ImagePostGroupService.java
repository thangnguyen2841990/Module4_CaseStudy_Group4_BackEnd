package com.codegym.module4casestudy.service.imagePostGroup;

import com.codegym.module4casestudy.model.entity.ImagePostGroup;
import com.codegym.module4casestudy.repository.IImagePostGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImagePostGroupService implements IImagePostGroupService {
    @Autowired
    private IImagePostGroupRepository iImagePostGroupRepository;
    @Override
    public Page<ImagePostGroup> findAll(Pageable pageable) {
        return iImagePostGroupRepository.findAll(pageable);
    }

    @Override
    public Optional<ImagePostGroup> findById(Long id) {
        return iImagePostGroupRepository.findById(id);
    }

    @Override
    public ImagePostGroup save(ImagePostGroup imagePostGroup) {
        return iImagePostGroupRepository.save(imagePostGroup);
    }

    @Override
    public void deleteById(Long id) {
        iImagePostGroupRepository.deleteById(id);
    }

    @Override
    public Iterable<ImagePostGroup> findAll() {
        return iImagePostGroupRepository.findAll();
    }

    @Override
    public ImagePostGroup[] listImage(Long postGroup) {
        return iImagePostGroupRepository.listImage(postGroup);
    }

    @Override
    public Optional<ImagePostGroup> findByPostGroupId(Long postGroupId) {
        return iImagePostGroupRepository.findByPostGroupId(postGroupId);
    }

    @Override
    public String findImageById(Long imageId) {
        return iImagePostGroupRepository.findImageById(imageId);
    }
}
