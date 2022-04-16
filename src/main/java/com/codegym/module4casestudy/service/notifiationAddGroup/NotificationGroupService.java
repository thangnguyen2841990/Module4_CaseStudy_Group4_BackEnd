package com.codegym.module4casestudy.service.notifiationAddGroup;

import com.codegym.module4casestudy.model.entity.NotificationAddGroup;
import com.codegym.module4casestudy.repository.INotificationAddGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationGroupService implements INotificationGroupService{
    @Autowired
    private INotificationAddGroupRepository notificationAddGroupRepository;

    @Override
    public Page<NotificationAddGroup> findAll(Pageable pageable) {
        return notificationAddGroupRepository.findAll(pageable);
    }

    @Override
    public Optional<NotificationAddGroup> findById(Long id) {
        return notificationAddGroupRepository.findById(id);
    }

    @Override
    public NotificationAddGroup save(NotificationAddGroup notificationAddGroup) {
        return notificationAddGroupRepository.save(notificationAddGroup);
    }

    @Override
    public void deleteById(Long id) {
        notificationAddGroupRepository.deleteById(id);

    }

    @Override
    public List<NotificationAddGroup> showNotificationAddGroup(Long toUserId) {
        return notificationAddGroupRepository.showNotificationAddGroup(toUserId);
    }

    @Override
    public Optional<NotificationAddGroup> findNotification(Long groupId, Long fromId) {
        return notificationAddGroupRepository.findNotification(groupId, fromId);
    }

    @Override
    public Optional<NotificationAddGroup> findNotificationByGroupIdAndFromUserId(Long fromUserId, Long groupId) {
        return Optional.empty();
    }


}
