package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.PostUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface IPostRepository extends JpaRepository<PostUser, Long> {
    @Modifying
    @Query(value = "call deletePost(?1)", nativeQuery = true)
    void deletePost(Long postUserId);

    @Query(value = "select * from post_user where user_info_id = ?1 ",nativeQuery = true)
    List<PostUser>  showAllPostByUser(Long userInfoId);





}
