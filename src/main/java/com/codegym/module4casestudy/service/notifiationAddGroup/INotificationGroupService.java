package com.codegym.module4casestudy.service.notifiationAddGroup;


import com.codegym.module4casestudy.model.entity.NotificationAddFriends;
import com.codegym.module4casestudy.model.entity.NotificationAddGroup;
import com.codegym.module4casestudy.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface INotificationGroupService extends IGeneralService<NotificationAddGroup> {
    List<NotificationAddGroup> showNotificationAddGroup(Long toUserId);
    Optional<NotificationAddGroup> findNotification(Long fromUserId, Long toUserId);
}
