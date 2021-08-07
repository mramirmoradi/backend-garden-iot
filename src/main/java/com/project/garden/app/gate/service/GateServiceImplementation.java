package com.project.garden.app.gate.service;

import com.project.garden.app.gate.model.Gate;
import com.project.garden.app.gate.repository.GateRepository;
import com.project.garden.app.phone.model.Phone;
import com.project.garden.core.Base.BaseServiceImp;
import com.project.garden.core.exception.Error;
import com.project.garden.core.exception.ErrorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GateServiceImplementation extends BaseServiceImp<Gate> implements GateService {

    private final GateRepository repository;
    @Autowired
    public GateServiceImplementation(GateRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void active(long gateId) {
        Gate gate = find(gateId);
        gate.setActive(true);
        repository.save(gate);
    }

    @Override
    public Gate findByPhone(Phone phone) {
        Optional<Gate> optionalGate = repository.findByPhone(phone);
        if (optionalGate.isEmpty())
            throw new Error(ErrorStatus.NOTFOUND, Gate.class.getSimpleName(), "gate not found", null);
        return optionalGate.get();
    }
}
