package org.project.easyNascar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
	scanBasePackages = {
		"org.project.easyNascar",
		"org.project.framework"
	}
)
@EntityScan(basePackages = {
    "org.project.easyNascar.models.entity",
    "org.project.framework.models.entity"  
})
@EnableFeignClients(basePackages = {"org.project.easyNascar", "org.project.framework"})
@EnableJpaRepositories(basePackages = {"org.project.easyNascar", "org.project.framework"})
public class EasyNascarApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyNascarApplication.class, args);
	}

}