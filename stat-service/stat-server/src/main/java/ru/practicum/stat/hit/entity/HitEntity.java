package ru.practicum.stat.hit.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Table(schema = "public", name = "endpoint_hits")
public class HitEntity {
    @Id
    private Long id;

    private String app;

    private String uri;

    private String ip;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column("timestamp_at")
    private LocalDateTime timestamp;
}
