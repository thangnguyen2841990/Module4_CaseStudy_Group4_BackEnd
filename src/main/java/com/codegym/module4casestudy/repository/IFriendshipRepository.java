package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFriendshipRepository extends JpaRepository<Friendship, Long> {

    Optional<Friendship> findFriendshipByFromUser_IdAndToUser_Id(Long from_user_id, Long to_user_id);

    Iterable<Friendship> findAllByStatus(Integer status);

}
