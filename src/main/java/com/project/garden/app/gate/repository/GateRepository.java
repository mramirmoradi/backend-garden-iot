package com.project.garden.app.gate.repository;

import com.project.garden.app.gate.model.Gate;
import com.project.garden.app.phone.model.Phone;
import com.project.garden.core.Base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GateRepository extends BaseRepository<Gate> {

    Optional<Gate> findByPhone(Phone phone);
}
