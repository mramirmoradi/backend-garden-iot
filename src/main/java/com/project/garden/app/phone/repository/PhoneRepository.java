package com.project.garden.app.phone.repository;

import com.project.garden.app.phone.model.Phone;
import com.project.garden.core.Base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends BaseRepository<Phone> {

    Optional<Phone> findByNumber(String number);
}
