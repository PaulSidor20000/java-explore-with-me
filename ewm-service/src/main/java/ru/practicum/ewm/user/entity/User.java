package ru.practicum.ewm.user.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(value = "user_id")
    private int id;

    @Column(value = "user_email")
    private String email;

    @Column(value = "user_name")
    private String name;

}
