package ru.practicum.ewm.locations.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

//@Data
//@Table(value = "locations")
@EqualsAndHashCode(callSuper = true)
public class Location extends AbstractLocation {

//    @Id
//    @Column(value = "location_id")
//    private Integer id;

//    @Column(value = "location_name")
//    private String name;

//    private Float lat;

//    private Float lon;

}