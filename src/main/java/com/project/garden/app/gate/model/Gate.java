package com.project.garden.app.gate.model;

import com.project.garden.app.Sensor.model.Sensor;
import com.project.garden.app.phone.model.Phone;
import com.project.garden.core.Base.BaseEntity;
import com.project.garden.security.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table
public class Gate extends BaseEntity {

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Phone phone;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Sensor> sensors;

    @Column
    private boolean active;

    @OneToOne
    private User user;
}
