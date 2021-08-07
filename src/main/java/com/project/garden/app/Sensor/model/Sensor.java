package com.project.garden.app.Sensor.model;

import com.project.garden.core.Base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table
public class Sensor extends BaseEntity {

    @Column
    private String name;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private SensorStatus status;


}
