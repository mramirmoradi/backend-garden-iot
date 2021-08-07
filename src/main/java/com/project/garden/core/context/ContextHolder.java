package com.project.garden.core.context;


import com.project.garden.security.user.model.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class ContextHolder {

    private Long userId;
    private Long gateId;
    private Role role;


}
