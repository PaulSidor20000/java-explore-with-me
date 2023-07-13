package ru.practicum.ewm.compilation.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Data
@Table(name = "compilations")
public class Compilation {

    @Id
    @Column(value = "compilation_id")
    private int id;

    @Column(value = "compilation_pinned")
    private boolean pinned;

    @Column(value = "compilation_title")
    private String title;

    @Transient
    private Set<Integer> events;

}
