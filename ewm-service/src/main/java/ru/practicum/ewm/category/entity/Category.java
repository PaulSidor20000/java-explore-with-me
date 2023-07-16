package ru.practicum.ewm.category.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "categories")
public class Category {

    @Id
    @Column(value = "category_id")
    private int id;

    @Column(value = "category_name")
    private String name;

}
