package com.lota.SafeVaultBankingApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
public class SafeVaultBankingApplication {

	 static public void main(String[] args) {
		SpringApplication.run(SafeVaultBankingApplication.class, args);
	}
}
