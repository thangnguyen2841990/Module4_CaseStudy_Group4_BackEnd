package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/userInfo")
public class UserInfoRestController {

    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping()
    public ResponseEntity<Page<UserInfo>> showAllUser(@PageableDefault(value = 10) Pageable pageable){
        Page<UserInfo> users = this.userInfoService.findAll(pageable);
        if (users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    //Tìm cụ thể thông tin của 1 user
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> findById(@PathVariable Long userId){
        Optional<UserInfo> userInfo = this.userInfoService.findByUserId(userId);
        if (!userInfo.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userInfo.get(), HttpStatus.OK);
    }

}
