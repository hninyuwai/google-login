package com.sample.google_login.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private  String firstName;

    private String lastName;

    private String userName;

    @Column(columnDefinition="text")
    private String token;

    @Column(unique=true)
    private String email;

    private String password;

}
