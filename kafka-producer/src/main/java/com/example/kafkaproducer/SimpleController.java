package com.example.kafkaproducer;

import com.example.kafkaproducer.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class SimpleController {
    @Autowired
    private MessageSender messageSender;

    @GetMapping
    public void produce() {
        System.out.println("get request");
        User user = new User();
        user.setName("Pavel");
        user.setId(2);
        user.setEmail("pavel@tut.by");
        System.out.println("created user " + user);
        messageSender.sendUser(user);
    }
}
