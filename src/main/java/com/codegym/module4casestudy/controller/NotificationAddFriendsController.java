package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.entity.NotificationAddFriends;
import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.service.notificationAddfriends.INotificationAddFriendsService;
import com.codegym.module4casestudy.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/addFriends")
public class NotificationAddFriendsController {
    @Autowired
    private INotificationAddFriendsService notificationAddfriendsService;

    @Autowired
    private IUserInfoService userInfoService;

    @PostMapping("/{fromUserId}/{toUserId}")
    public ResponseEntity<NotificationAddFriends> createNewNotificationAddFriends(@PathVariable Long fromUserId, @PathVariable Long toUserId){
        UserInfo fromUserInfo = this.userInfoService.findByUserId(fromUserId).get();

        UserInfo toUserInfo = this.userInfoService.findByUserId(toUserId).get();

        NotificationAddFriends notificationAddfriends = new NotificationAddFriends();
        notificationAddfriends.setFromUser(fromUserInfo);
        notificationAddfriends.setToUser(toUserInfo);
        notificationAddfriends.setStatus(0);
        notificationAddfriendsService.save(notificationAddfriends);
        return new ResponseEntity<>(notificationAddfriends, HttpStatus.OK);
    }
    @GetMapping("showNotification/{toUserId}")
    public ResponseEntity<List<NotificationAddFriends>> showAllNotificationOfToUSerId(@PathVariable Long toUserId){
        UserInfo toUserInfo = this.userInfoService.findByUserId(toUserId).get();
        List<NotificationAddFriends> listNotifications = this.notificationAddfriendsService.showNotifications(toUserInfo.getId());
        return new ResponseEntity<>(listNotifications,HttpStatus.OK);
    }


}
