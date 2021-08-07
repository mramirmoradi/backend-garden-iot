package com.project.garden.core.context;

public interface ContextHolderService {

    ContextHolder getUserState();

    void setUserState(ContextHolder contextHolder);

}
