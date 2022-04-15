package com.codegym.module4casestudy.service.likePostGroup;

import com.codegym.module4casestudy.model.entity.LikePostGroup;
import com.codegym.module4casestudy.repository.IImagePostGroupRepository;
import com.codegym.module4casestudy.repository.ILikePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikePostGroupService implements ILikePostGroupService {
    @Autowired
    private ILikePostGroupService iLikePostGroupService;

    @Override
    public Page<LikePostGroup> findAll(Pageable pageable) {
        return iLikePostGroupService.findAll(pageable);
    }

    @Override
    public Optional<LikePostGroup> findById(Long id) {
        return iLikePostGroupService.findById(id);
    }

    @Override
    public LikePostGroup save(LikePostGroup likePostGroup) {
        return iLikePostGroupService.save(likePostGroup);
    }

    @Override
    public void deleteById(Long id) {
        iLikePostGroupService.deleteById(id);
    }

    @Override
    public List<LikePostGroup> findAll() {
        return iLikePostGroupService.findAll();
    }

    @Override
    public Optional<LikePostGroup> findLikePostGroupByGroupMemberIdAndPostGroupId(Long groupMemberId, Long postGroupId) {
        return iLikePostGroupService.findLikePostGroupByGroupMemberIdAndPostGroupId(groupMemberId, postGroupId);
    }

    @Override
    public List<LikePostGroup> totalLikeByPost(Long postGroupId) {
        return iLikePostGroupService.totalLikeByPost(postGroupId);
    }
}
