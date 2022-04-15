package com.codegym.module4casestudy.service.userInfo;

import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.repository.IUserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements IUserInfoService {
    @Autowired
    private IUserInfoRepository userInfoRepository;

    @Override
    public Page<UserInfo> findAll(Pageable pageable) {
        return userInfoRepository.findAll(pageable);
    }

    @Override
    public Optional<UserInfo> findById(Long id) {
        return userInfoRepository.findById(id);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void deleteById(Long id) {
        userInfoRepository.deleteById(id);
    }

    @Override
    public Long findUserId(String email, String phone_number) {
        return userInfoRepository.findUserId(email, phone_number);
    }

    @Override
    public Optional<UserInfo> findByUserId(Long userId) {
        return userInfoRepository.findByUserId(userId);
    }

    @Override
    public UserInfo findByEmail(String email) {
        return userInfoRepository.findByEmail(email);
    }

    public void updateResetPasswordToken(String token, String email) throws NotFoundException {
        UserInfo userInfo = userInfoRepository.findByEmail(email);
        if(userInfo !=null){
            userInfo.setResetPasswordToken(token);
            userInfoRepository.save(userInfo);
        }else {
            throw new NotFoundException("Could not find any customer with the email " + email);
        }
    }

    public UserInfo getByResetPasswordToken(String token){
        return userInfoRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(UserInfo userInfo, String newPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(newPassword);
        userInfo.setPassword(encodePassword);

        userInfo.setResetPasswordToken(null);
        userInfoRepository.save(userInfo);
    }
}
