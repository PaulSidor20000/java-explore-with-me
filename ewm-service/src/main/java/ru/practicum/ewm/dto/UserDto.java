package ru.practicum.ewm.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserDto {

    @Email(regexp = "^[a-z0-9-_.%]+@[a-z0-9-_]+.[a-z]+$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "email is not valid")
    private String email;

    private int id;

    @NotEmpty(message = "Must not be empty")
    private String name;

}
