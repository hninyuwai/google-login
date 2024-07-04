package com.sample.google_login.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private  String userId;

    private String firstName;

    private String lastName;

    private String userName;

    private String token;

    private String email;
}
