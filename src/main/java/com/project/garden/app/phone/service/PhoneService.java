package com.project.garden.app.phone.service;

import com.project.garden.app.phone.model.Phone;
import com.project.garden.core.Base.BaseService;

public interface PhoneService extends BaseService<Phone> {

    public boolean isExist(String phoneNumber);

    public Phone findByNumber(String phoneNumber);
}
