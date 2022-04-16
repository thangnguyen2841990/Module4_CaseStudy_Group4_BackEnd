package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.PostGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface IPostGroupRepository extends JpaRepository<PostGroup, Long> {
    @Query(value = "select * from post_group where group_id = ?1", nativeQuery = true)
    List<PostGroup> findByGroup(Long id);
    @Modifying
    @Query(value = "call deletePostGroup(?1)", nativeQuery = true)
    void deletePostGroup(Long postGroupId);
}
