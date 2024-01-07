package com.example.recipeapi.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequest {
    public String email;
    public String password;
}
