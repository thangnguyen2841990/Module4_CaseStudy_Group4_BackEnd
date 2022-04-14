package com.codegym.module4casestudy.repository;

import com.codegym.module4casestudy.model.dto.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupMemberRepository extends JpaRepository<GroupMember, Long> {
}
