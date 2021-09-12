package com.swvalerian.springrestapi.rest;

import lombok.Data;

@Data

public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
