package com.example.recipeapi.models;

import lombok.Builder;

@Builder
public class AuthenticationResponse {
    public String jwtToken;
    public String expiration;
}
