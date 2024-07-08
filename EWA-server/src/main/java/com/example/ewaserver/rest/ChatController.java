package com.example.ewaserver.rest;

import com.example.ewaserver.models.Chat;
import com.example.ewaserver.models.User;
import com.example.ewaserver.notifications.NotificationDistributor;
import com.example.ewaserver.repositories.ChatRepository;
import com.example.ewaserver.repositories.UserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatRepository Repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationDistributor notificationDistributor;

    @GetMapping(path = "/getAll", produces = "application/json")
    public List<Chat> getAllMessages() {
        return this.Repository.findAll();
    }

    @PostMapping(path = "/friend/{id}", produces = "application/json")
    public String sendMessage(@RequestBody ObjectNode chatInfo, @PathVariable int id) {
        Chat chat = new Chat(LocalDateTime.now(), chatInfo.get("message").asText());
        User fromUser = this.userRepository.findById(chatInfo.get("id").asInt());
        User toUser = this.userRepository.findById(id);
        chat.setFromUser(fromUser);
        chat.setToUser(toUser);
        this.Repository.Save(chat);
        String chatLink;
        if (fromUser.getUserId() < toUser.getUserId()){
            chatLink = fromUser.getUserId() + "&" + toUser.getUserId();
        }else{
            chatLink = toUser.getUserId() + "&" + fromUser.getUserId();
        }
        this.notificationDistributor.notify("chat" + chatLink);
        return "Message is successfully send!";
    }


    @GetMapping(path = "/{id}&{id2}", produces = "application/json")
    public List<Chat> getMessageForFriend(@PathVariable long id, @PathVariable long id2) {
        return this.Repository.chatsWithFriend(id,id2);
    }

    @GetMapping(path = "/searchUser/{keyword}", produces = "application/json")
    public List<User> getSearchedUsers (@PathVariable String keyword){
        return this.Repository.searchUsersByUsername(keyword);
    }

    @PostMapping(path = "/addFriend", produces = "application/json")
    public boolean addFriend(@RequestBody ObjectNode userAndFriend){
        User user = this.userRepository.findById(userAndFriend.get("userId").asInt());
        User friend = this.userRepository.findById(userAndFriend.get("friendId").asInt());
        user.addFriend(friend);
        this.userRepository.Save(user);
        return true;
    }

    @GetMapping(path = "/friends/getAll/{id}")
    public Set<User> getFriends(@PathVariable int id) {
        User user = this.userRepository.findById(id);
        return user.getFriends();
    }

}
