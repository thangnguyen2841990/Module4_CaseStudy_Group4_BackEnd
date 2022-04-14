package com.codegym.module4casestudy.service.groupMember;

import com.codegym.module4casestudy.model.dto.GroupMember;
import com.codegym.module4casestudy.repository.IGroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class GroupMemberService implements IGroupMemberService{
    @Autowired
    private IGroupMemberRepository groupMemberRepository;


    @Override
    public Page<GroupMember> findAll(Pageable pageable) {
        return groupMemberRepository.findAll(pageable);
    }

    @Override
    public Optional<GroupMember> findById(Long id) {
        return groupMemberRepository.findById(id);
    }

    @Override
    public GroupMember save(GroupMember groupMember) {
        return groupMemberRepository.save(groupMember);
    }

    @Override
    public void deleteById(Long id) {
    groupMemberRepository.deleteById(id);
    }
}
