package com.project.garden.app.phone.model;

import com.project.garden.core.Base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table
public class Phone extends BaseEntity {

    @Column(unique = true)
    @NotNull
    private String number;
}
