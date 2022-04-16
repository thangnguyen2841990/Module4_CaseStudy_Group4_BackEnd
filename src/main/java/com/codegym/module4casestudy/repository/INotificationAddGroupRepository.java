package com.codegym.module4casestudy.repository;


import com.codegym.module4casestudy.model.entity.NotificationAddGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INotificationAddGroupRepository extends JpaRepository<NotificationAddGroup, Long> {
    @Query(value = "select * from notification_add_groups where to_user_id = ?1", nativeQuery = true)
    List<NotificationAddGroup> showNotificationAddGroup(Long toUserId);

    @Query(value = "select * from notification_add_groups where to_user_id =?1 and from_user_id", nativeQuery = true)
    Optional<NotificationAddGroup> findNotification(Long fromUserId, Long toUserId);
}
