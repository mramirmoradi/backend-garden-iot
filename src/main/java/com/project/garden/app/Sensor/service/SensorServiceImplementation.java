package com.project.garden.app.Sensor.service;

import com.project.garden.app.Sensor.SensorService;
import com.project.garden.app.Sensor.model.Sensor;
import com.project.garden.core.Base.BaseRepository;
import com.project.garden.core.Base.BaseServiceImp;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImplementation extends BaseServiceImp<Sensor> implements SensorService {
    public SensorServiceImplementation(BaseRepository<Sensor> repository) {
        super(repository);
    }
}
