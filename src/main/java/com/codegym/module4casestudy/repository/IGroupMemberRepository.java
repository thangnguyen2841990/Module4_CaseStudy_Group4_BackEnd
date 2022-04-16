package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.Group;
import com.codegym.module4casestudy.model.entity.GroupMember;
import com.codegym.module4casestudy.model.entity.NotificationAddGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGroupMemberRepository extends JpaRepository<GroupMember, Long> {
    @Query(value = "select * from notification_add_groups where from_user_id = ?1 and  group_id = ?2", nativeQuery = true)
    Optional<NotificationAddGroup> findNotificationAccept(Long fromUserId, Long groupId);

    @Query(value = "select * from group_members where group_id = ?1", nativeQuery = true)
    List<GroupMember> findGroupMemberByGroupId(Long groupId);

}
