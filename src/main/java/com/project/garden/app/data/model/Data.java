package com.project.garden.app.data.model;

import com.project.garden.app.gate.model.Gate;
import com.project.garden.core.Base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@lombok.Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Data extends BaseEntity {

    @Column
    private Double hin;

    @Column
    private Double hout;

    @Column
    private Double tin;

    @Column
    private Double tout;

    @Column
    private Double co;

    @Column
    private Double iux;

    @ManyToOne
    private Gate gate;
}
