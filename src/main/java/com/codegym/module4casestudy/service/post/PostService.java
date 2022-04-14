package com.codegym.module4casestudy.service.post;

import com.codegym.module4casestudy.model.entity.PostUser;
import com.codegym.module4casestudy.repository.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService{

    @Autowired
    private IPostRepository postRepository;

    @Override
    public Page<PostUser> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Optional<PostUser> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public PostUser save(PostUser postUser) {
        return postRepository.save(postUser);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Iterable<PostUser> findAll() {
        return postRepository.findAll();
    }

    @Override
    public void deletePost(Long postUserId) {
        this.postRepository.deletePost(postUserId);
    }
}
