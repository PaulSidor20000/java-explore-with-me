package ru.practicum.ewm.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class NewUserRequest {

    @Range(min = 6, max = 254)
    private String email;

    @Range(min = 2, max = 250)
    private String name;
}
