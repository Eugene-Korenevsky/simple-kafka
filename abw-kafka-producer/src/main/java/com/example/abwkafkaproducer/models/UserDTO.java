package com.example.abwkafkaproducer.models;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private RoleDTO role;
}
