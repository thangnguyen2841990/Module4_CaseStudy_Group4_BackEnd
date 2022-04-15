package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.NotificationAddFriends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifiCationRepository extends JpaRepository<NotificationAddFriends, Long> {
    @Query(value = "select * from notification_add_friends where to_user_id = ?1",nativeQuery = true)
    List<NotificationAddFriends>  showNotifications(Long toUserId);

}
