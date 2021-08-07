package com.project.garden.security.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.garden.app.phone.model.Phone;
import com.project.garden.core.Base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;
    public static final String USERNAME_PATTERN = "^[0-9a-zA-Z@._-]{4,255}$";

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank
    @Column(unique = true)
    @Pattern(regexp = USERNAME_PATTERN)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    private Phone phone;

    @Column
    private String email;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
