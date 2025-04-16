package org.project.easyf1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EasyF1Application {

	public static void main(String[] args) {
		SpringApplication.run(EasyF1Application.class, args);
	}

}