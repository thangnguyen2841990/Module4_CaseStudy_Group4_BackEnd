package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface INotificationRepository extends JpaRepository<Notification, Long> {

}
