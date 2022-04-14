package com.codegym.module4casestudy.service.friendship;

import com.codegym.module4casestudy.model.entity.Friendship;
import com.codegym.module4casestudy.repository.IFriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendshipService implements IFriendshipService {
    @Autowired
    private IFriendshipRepository iFriendshipRepository;

    @Override
    public Optional<Friendship> findFriendshipByFromUser_IdAndToUser_Id(Long from_user_id, Long to_user_id) {
        return iFriendshipRepository.findFriendshipByFromUser_IdAndToUser_Id(from_user_id, to_user_id);
    }

    @Override
    public Iterable<Friendship> findAll() {
        return iFriendshipRepository.findAll();
    }

    @Override
    public Page<Friendship> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Friendship> findById(Long id) {
        return iFriendshipRepository.findById(id);
    }

    @Override
    public Friendship save(Friendship friendship) {
        return iFriendshipRepository.save(friendship);
    }

    @Override
    public void deleteById(Long id) {
        iFriendshipRepository.deleteById(id);
    }



    @Override
    public Iterable<Friendship> findAllByStatus(Integer status) {
        return iFriendshipRepository.findAllByStatus(status);
    }
}
