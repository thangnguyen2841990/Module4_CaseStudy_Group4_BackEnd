package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.entity.NotificationAddGroup;
import com.codegym.module4casestudy.model.entity.UserInfo;

import com.codegym.module4casestudy.service.notifiationAddGroup.INotificationGroupService;
import com.codegym.module4casestudy.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/notificationAddGroups")
public class NotificationAddGroupController {
    @Autowired
    private INotificationGroupService notificationGroupService;
    @Autowired
    private IUserInfoService userInfoService;

    @PostMapping("/{fromUserId}/{toUserId}")
    public ResponseEntity<NotificationAddGroup> createNotificationAddGroup(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        UserInfo fromUser = userInfoService.findByUserId(fromUserId).get();
        UserInfo toUser = userInfoService.findByUserId(toUserId).get();
        Optional<NotificationAddGroup> optionalNotificationAddGroup = notificationGroupService.findNotification(fromUserId, toUserId);
        if (!optionalNotificationAddGroup.isPresent()) {
            NotificationAddGroup notificationAddGroup = new NotificationAddGroup();
            notificationAddGroup.setFromUser(fromUser);
            notificationAddGroup.setToUser(toUser);
            notificationAddGroup.setStatus(0);

            notificationGroupService.save(notificationAddGroup);
            return new ResponseEntity<>(notificationAddGroup, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @GetMapping("/showNotificationAddGroup/{toUserId}")
    public ResponseEntity<List<NotificationAddGroup>> showAllNotificationAddGroup(@PathVariable Long toUserId) {
        UserInfo userInfo = userInfoService.findByUserId(toUserId).get();
        List<NotificationAddGroup> notificationAddGroupList = this.notificationGroupService.showNotificationAddGroup(userInfo.getId());
        return new ResponseEntity<>(notificationAddGroupList, HttpStatus.OK);
    }

}
