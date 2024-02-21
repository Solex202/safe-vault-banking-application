package com.lota.SafeVaultBankingApplication.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class AppUtil {

    public static final String EMAIL= "email";
    public static final String APPLICATION_JSON= "application/json";

    public static Pageable paginateDataWith(Integer page, Integer size){
        if(page < 1) page = ZERO.intValue();
        else page = page - ONE.intValue();

        if(size < 1) size = 15;
        return PageRequest.of(page, size, Sort.Direction.DESC, "id");
    }
}
