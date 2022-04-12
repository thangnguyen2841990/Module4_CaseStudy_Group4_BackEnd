package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.PostUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends JpaRepository<PostUser,Long> {


}
