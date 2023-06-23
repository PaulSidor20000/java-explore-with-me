package ru.practicum.ewm.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class NewUserRequest {

    @NotBlank
    @Size(min = 6, max = 254)
    @Email(regexp = "^[a-z0-9-_.%]+@[a-z0-9-_]+.[a-z]+$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "email is not valid")
    private String email;

    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}
