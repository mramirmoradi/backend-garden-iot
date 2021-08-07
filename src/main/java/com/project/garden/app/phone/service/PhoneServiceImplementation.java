package com.project.garden.app.phone.service;

import com.project.garden.app.phone.model.Phone;
import com.project.garden.app.phone.repository.PhoneRepository;
import com.project.garden.core.Base.BaseServiceImp;
import com.project.garden.core.exception.Error;
import com.project.garden.core.exception.ErrorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhoneServiceImplementation extends BaseServiceImp<Phone> implements PhoneService{

    private final PhoneRepository repository;
    @Autowired
    public PhoneServiceImplementation(PhoneRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public boolean isExist(String phoneNumber) {
        return repository.findByNumber(phoneNumber).isPresent();
    }

    @Override
    public Phone findByNumber(String phoneNumber) {
        Optional<Phone> optionalPhone = repository.findByNumber(phoneNumber);
        if (optionalPhone.isPresent())
            return optionalPhone.get();
        else
            throw new Error(ErrorStatus.PHONE_NOTFOUND, Phone.class.getSimpleName(), "phone not found", null);
    }
}
