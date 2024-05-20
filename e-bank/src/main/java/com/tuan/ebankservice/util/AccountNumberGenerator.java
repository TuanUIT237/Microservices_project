package com.tuan.ebankservice.util;

import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
@Slf4j
public class AccountNumberGenerator implements IdentifierGenerator {
    @NonFinal
    @Value("${app.min}")
    protected int MIN;

    @NonFinal
    @Value("${app.max}")
    protected int MAX;
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefix = "023";
        String suffix ="";
        try{
            int min= MIN;
            int max = MAX;
            int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
            suffix = String.valueOf(randNumber);
        }catch (Exception e){
            log.info("Account number existed");
        }
        return prefix + suffix;
    }
}
