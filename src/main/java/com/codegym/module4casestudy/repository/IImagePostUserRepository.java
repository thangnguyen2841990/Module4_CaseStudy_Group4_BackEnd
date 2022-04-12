package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.ImagePostUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImagePostUserRepository extends JpaRepository<ImagePostUser, Long> {


}
