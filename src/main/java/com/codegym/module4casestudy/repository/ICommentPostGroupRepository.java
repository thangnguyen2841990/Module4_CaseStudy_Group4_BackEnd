package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.CommentPostGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentPostGroupRepository  extends JpaRepository<CommentPostGroup, Long> {
    @Query(value = "select * from comment_post_group where post_group_id = ?1", nativeQuery = true)
    List<CommentPostGroup> displayAllCommentOfPostGroup(Long postGroupId);
}
