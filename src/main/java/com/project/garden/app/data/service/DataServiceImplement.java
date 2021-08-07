package com.project.garden.app.data.service;

import com.project.garden.app.data.model.Data;
import com.project.garden.app.data.repository.DataRepository;
import com.project.garden.app.gate.service.GateService;
import com.project.garden.core.Base.BaseServiceImp;
import com.project.garden.core.context.ContextHolderService;
import com.project.garden.core.exception.Error;
import com.project.garden.core.exception.ErrorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImplement extends BaseServiceImp<Data> implements DataService{

    private final DataRepository repository;
    private final ContextHolderService contextHolderService;
    private final GateService gateService;
    @Autowired
    public DataServiceImplement(DataRepository repository, ContextHolderService contextHolderService, GateService gateService) {
        super(repository);
        this.repository = repository;
        this.contextHolderService = contextHolderService;
        this.gateService = gateService;
    }

    @Override
    public Data submit(Data data) {
        Long gateId = contextHolderService.getUserState().getGateId();
        if (gateId == null)
            throw new Error(ErrorStatus.UNAUTHORIZED, Data.class.getSimpleName(), "permission denied", null);
        data.setGate(gateService.find(gateId));
        return repository.save(data);
    }

}
