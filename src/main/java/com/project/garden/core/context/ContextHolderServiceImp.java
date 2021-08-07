package com.project.garden.core.context;

import com.project.garden.core.ThreadLocal;
import org.springframework.stereotype.Service;

@Service
public class ContextHolderServiceImp implements ContextHolderService {
    private static final String KEY = "USER_STATE";

    @Override
    public ContextHolder getUserState() {
        return (ContextHolder) ThreadLocal.getObject(KEY);
    }

    @Override
    public void setUserState(ContextHolder contextHolder) {
        ThreadLocal.setObject(KEY, contextHolder);
    }
}
