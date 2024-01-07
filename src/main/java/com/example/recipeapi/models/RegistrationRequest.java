package com.example.recipeapi.models;

import lombok.Data;


@Data
public class RegistrationRequest {
    public String email;
    public String password;
    public String confirmPassword;
}
