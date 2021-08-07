package com.project.garden.core.otp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.project.garden.app.phone.model.Phone;
import com.project.garden.core.exception.Error;
import com.project.garden.core.exception.ErrorStatus;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    private static final Integer EXPIRE_TIME = 5;

    private final LoadingCache<String, Integer> otpcache;

    public OTPService() {
        super();
        otpcache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_TIME, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) throws Exception {
                return null;
            }
        });
    }

    public int generateOTP(Phone phone) {
        int otp = 100000 + new Random().nextInt(900000);
        otpcache.put(phone.getNumber(), otp);
        return otp;
    }

    public int getOTP(Phone phone) {
        try {
            return otpcache.get(phone.getNumber());
        } catch (Exception e) {
            throw new Error(ErrorStatus.PHONE_NOTFOUND, Phone.class.getSimpleName(), "PHONE NOT FOUND", null);
        }
    }

    public boolean isExist(Phone phone) {
        try {
            otpcache.get(phone.getNumber());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clearOTP(Phone phone) {
        try {
            otpcache.invalidate(phone.getNumber());
        } catch (Exception e) {
            throw new Error(ErrorStatus.PHONE_NOTFOUND, Phone.class.getSimpleName(), "PHONE NOT FOUND", null);
        }
    }
}
