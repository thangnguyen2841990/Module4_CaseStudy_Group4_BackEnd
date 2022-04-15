package com.codegym.module4casestudy.service.notificationAddfriends;

import com.codegym.module4casestudy.model.entity.NotificationAddFriends;
import com.codegym.module4casestudy.service.IGeneralService;

import java.util.List;

public interface INotificationAddFriendsService extends IGeneralService<NotificationAddFriends> {
    List<NotificationAddFriends> showNotifications(Long toUserId);

}
