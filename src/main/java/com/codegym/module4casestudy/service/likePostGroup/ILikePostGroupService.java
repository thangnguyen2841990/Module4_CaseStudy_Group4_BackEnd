package com.codegym.module4casestudy.service.likePostGroup;

import com.codegym.module4casestudy.model.entity.LikePostGroup;
import com.codegym.module4casestudy.model.entity.LikePostUser;
import com.codegym.module4casestudy.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ILikePostGroupService extends IGeneralService<LikePostGroup> {
    List<LikePostGroup> findAll();

    Optional<LikePostGroup> findLikePostGroupByGroupMemberIdAndPostGroupId(Long groupMemberId, Long postGroupId);

    List<LikePostGroup> totalLikeByPost(Long postGroupId);
}
