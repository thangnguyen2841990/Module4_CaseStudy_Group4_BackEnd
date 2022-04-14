package com.codegym.module4casestudy.service.friendship;


import com.codegym.module4casestudy.model.entity.Friendship;
import com.codegym.module4casestudy.service.IGeneralService;

import java.util.Optional;

public interface IFriendshipService extends IGeneralService<Friendship> {

    Optional<Friendship> findFriendshipByFromUser_IdAndToUser_Id(Long from_user_id, Long to_user_id);

    Iterable<Friendship> findAllByStatus(Integer status);
    Iterable<Friendship> findAll();


}
