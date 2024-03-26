package com.lota.SafeVaultBankingApplication.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
