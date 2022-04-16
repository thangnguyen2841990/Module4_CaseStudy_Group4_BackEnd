package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.LikePostGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ILikePostGroupRepository  extends JpaRepository<LikePostGroup, Long> {
    @Query(value = "select * from like_post_group where group_id = ?1 and post_group_id = ?2", nativeQuery = true)
    Optional<LikePostGroup> findLikePostGroupByGroupMemberIdAndPostGroupId(Long groupMemberId, Long postGroupId);

    @Query(value = "select  * from like_post_group where post_group_id = ?1",nativeQuery = true)
    List<LikePostGroup> totalLikeByPost(Long postUserId);

}
