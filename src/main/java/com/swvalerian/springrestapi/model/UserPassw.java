package com.swvalerian.springrestapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "userpassw")
public class UserPassw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role4Security role;
}
