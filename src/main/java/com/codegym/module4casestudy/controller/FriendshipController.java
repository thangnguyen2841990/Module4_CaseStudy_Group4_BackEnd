package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.entity.Friendship;
import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.service.friendship.IFriendshipService;
import com.codegym.module4casestudy.service.user.IUserService;
import com.codegym.module4casestudy.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/friends")
public class FriendshipController {

    @Autowired
    private IFriendshipService friendshipService;

    @Autowired
    private IUserInfoService userService;

    @GetMapping
    public ResponseEntity<Iterable<Friendship>> showAll() {
        Iterable<Friendship> friendships = friendshipService.findAll();

        if (!friendships.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(friendships, HttpStatus.OK);
        }
    }

    @PostMapping("/notification/{userinfo_id}")
    public ResponseEntity<Iterable<Friendship>> getNotificationAcceptFriendships(@PathVariable("userinfo_id") long userinfo_id) {
        List<Friendship> friendships = (List<Friendship>) friendshipService.findAllByStatus(0);
        List<Friendship> friendshipList = new ArrayList<>();
        for (Friendship friendship : friendships) {
            if (friendship.getToUser().getId() == userinfo_id) {
                friendshipList.add(friendship);
            }
        }
        return new ResponseEntity<>(friendshipList, HttpStatus.OK);
    }

    @PostMapping("/{from_user_id}/{to_user_id}")
    public ResponseEntity<Friendship> addFriend(@PathVariable("from_user_id") long from_user_id, @PathVariable("to_user_id") long to_user_id) {
        UserInfo fromUser = userService.findById(from_user_id).get();
        UserInfo toUser = userService.findById(to_user_id).get();

        Optional<Friendship> optionalFriendship = friendshipService.findFriendshipByFromUser_IdAndToUser_Id(fromUser.getId(), toUser.getId());
        Friendship friendship = null;
        if (!optionalFriendship.isPresent()) {
            friendship = new Friendship(fromUser, toUser, 0);
            friendshipService.save(friendship);
        }

        return new ResponseEntity<>(friendship, HttpStatus.CREATED);
    }

    @PutMapping("/{from_user_id}/{to_user_id}")
    public ResponseEntity<Friendship> acceptFriend(@PathVariable("from_user_id") long from_user_id, @PathVariable("to_user_id") long to_user_id) {
        UserInfo fromUser = userService.findById(from_user_id).get();
        UserInfo toUser = userService.findById(to_user_id).get();

        Optional<Friendship> optionalFriendship = friendshipService.findFriendshipByFromUser_IdAndToUser_Id(fromUser.getId(), toUser.getId());
        if (optionalFriendship.isPresent()) {
            optionalFriendship.get().setStatus(1);
            friendshipService.save(optionalFriendship.get());
        }

        return new ResponseEntity<>(optionalFriendship.get(), HttpStatus.OK);
    }

    @PutMapping("/block/{from_user_id}/{to_user_id}")
    public ResponseEntity<Friendship> blockFriend(@PathVariable("from_user_id") long from_user_id, @PathVariable("to_user_id") long to_user_id) {
        UserInfo fromUser = userService.findById(from_user_id).get();
        UserInfo toUser = userService.findById(to_user_id).get();

        Optional<Friendship> optionalFriendship = friendshipService.findFriendshipByFromUser_IdAndToUser_Id(fromUser.getId(), toUser.getId());
        if (optionalFriendship.isPresent()) {
            optionalFriendship.get().setStatus(2);
            friendshipService.save(optionalFriendship.get());
        }

        Optional<Friendship> optionalFriendship1 = friendshipService.findFriendshipByFromUser_IdAndToUser_Id( toUser.getId(), fromUser.getId());
        if (optionalFriendship1.isPresent()) {
            optionalFriendship1.get().setStatus(2);
            friendshipService.save(optionalFriendship1.get());
        }

        if (!optionalFriendship.isPresent() && !optionalFriendship1.isPresent()) {
            Friendship friendship = new Friendship(fromUser, toUser, 2);
            friendshipService.save(friendship);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{from_user_id}/{to_user_id}")
    public ResponseEntity<Friendship> unFriend(@PathVariable("from_user_id") long from_user_id, @PathVariable("to_user_id") long to_user_id) {
        UserInfo fromUser = userService.findById(from_user_id).get();
        UserInfo toUser = userService.findById(to_user_id).get();

        Optional<Friendship> optionalFriendship = friendshipService.findFriendshipByFromUser_IdAndToUser_Id(fromUser.getId(), toUser.getId());
        if (optionalFriendship.isPresent()) {
            friendshipService.deleteById(optionalFriendship.get().getId());
        }
        Optional<Friendship> optionalFriendship1 = friendshipService.findFriendshipByFromUser_IdAndToUser_Id(toUser.getId(), fromUser.getId());
        if (optionalFriendship1.isPresent()) {
            friendshipService.deleteById(optionalFriendship1.get().getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-friendship/{from_user_id}/{to_user_id}")
    public ResponseEntity<?> getFriendship(@PathVariable("from_user_id") long from_user_id, @PathVariable("to_user_id") long to_user_id) {
        Optional<Friendship> friendshipOptional = friendshipService.findFriendshipByFromUser_IdAndToUser_Id(from_user_id, to_user_id);
        Optional<Friendship> friendshipOptional1 = friendshipService.findFriendshipByFromUser_IdAndToUser_Id(to_user_id, from_user_id);
        if (friendshipOptional.isPresent() && friendshipOptional.get().getStatus() == 1) {
            return new ResponseEntity<>(friendshipOptional.get().getToUser(), HttpStatus.OK);
        } else if (friendshipOptional1.isPresent() && friendshipOptional1.get().getStatus() == 1) {
            return new ResponseEntity<>(friendshipOptional1.get().getFromUser(), HttpStatus.OK);
        }
        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
