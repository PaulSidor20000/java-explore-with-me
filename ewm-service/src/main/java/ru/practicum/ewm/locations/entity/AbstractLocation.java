package ru.practicum.ewm.locations.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(value = "locations")
public abstract class AbstractLocation {

    @Id
    @Column(value = "location_id")
    protected Integer id;

    @Column(value = "location_name")
    protected String name;

    protected Float lat;

    protected Float lon;

}