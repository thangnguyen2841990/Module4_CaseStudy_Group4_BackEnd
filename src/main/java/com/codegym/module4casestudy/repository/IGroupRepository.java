package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IGroupRepository extends JpaRepository<Group, Long> {
    @Modifying // dùng đẻ triển khai câu query vì bảng này liên kiết với product nên gọi để xóa hết
    @Query(value = "call deleteGroup(?1)",nativeQuery = true)
    void deleteGroup(Long id);
}
