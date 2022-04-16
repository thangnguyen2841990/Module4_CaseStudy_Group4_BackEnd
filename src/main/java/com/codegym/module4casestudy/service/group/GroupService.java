package com.codegym.module4casestudy.service.group;

import com.codegym.module4casestudy.model.entity.Group;
import com.codegym.module4casestudy.repository.IGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService implements IGroupService{
    @Autowired
    private IGroupRepository groupRepository;

    @Override
    public Page<Group> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void deleteById(Long id) {
    groupRepository.deleteGroup(id);
    }

    @Override
    public Page<Group> findByUserId(Long id, Pageable pageable) {
        return groupRepository.findAllByUserId(id, pageable);
    }

    @Override
    public Page<Group> findAllGroupOtherUserId(Long id, Pageable pageable) {
        return groupRepository.findAllGroupOtherUserId(id, pageable);
    }
}
