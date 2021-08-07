package com.project.garden.app.gate.service;

import com.project.garden.app.gate.model.Gate;
import com.project.garden.app.phone.model.Phone;
import com.project.garden.core.Base.BaseService;

public interface GateService extends BaseService<Gate> {

    void active(long gateId);

    Gate findByPhone(Phone phone);
}
