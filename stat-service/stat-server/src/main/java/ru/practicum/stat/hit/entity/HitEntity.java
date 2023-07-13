package ru.practicum.stat.hit.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HitEntity hitEntity = (HitEntity) o;
        return Objects.equals(id, hitEntity.id) && Objects.equals(app, hitEntity.app) && Objects.equals(uri, hitEntity.uri) && Objects.equals(ip, hitEntity.ip) && Objects.equals(timestamp, hitEntity.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, app, uri, ip, timestamp);
    }
}
