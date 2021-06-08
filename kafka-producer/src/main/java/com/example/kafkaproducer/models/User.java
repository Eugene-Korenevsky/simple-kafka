package com.example.kafkaproducer.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private String name;
    private long id;
    private String email;
}
