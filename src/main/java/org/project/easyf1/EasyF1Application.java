package org.project.easyf1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
	scanBasePackages = {
		"org.project.easyf1",
		"org.project.framework"
	}
)
@EnableFeignClients(basePackages = {"org.project.easyf1", "org.project.framework"})
@EnableJpaRepositories(basePackages = {"org.project.easyf1", "org.project.framework"})
public class EasyF1Application {

	public static void main(String[] args) {
		SpringApplication.run(EasyF1Application.class, args);
	}

}