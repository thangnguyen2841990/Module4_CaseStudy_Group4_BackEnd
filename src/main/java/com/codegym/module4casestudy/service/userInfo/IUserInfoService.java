package com.codegym.module4casestudy.service.userInfo;

import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.service.IGeneralService;

import java.util.Optional;

public interface IUserInfoService extends IGeneralService<UserInfo> {
    Long findUserId(String email, String phone_number) ;

    Optional<UserInfo> findByUserId(Long userId);

    UserInfo findByEmail(String email);

}
