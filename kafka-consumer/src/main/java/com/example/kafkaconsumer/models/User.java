package com.example.kafkaconsumer.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private long id;
    private String email;
    private String name;
}
