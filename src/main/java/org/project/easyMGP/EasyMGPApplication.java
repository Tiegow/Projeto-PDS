package org.project.easyMGP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
	scanBasePackages = {
		"org.project.easyMGP",
		"org.project.framework"
	}
)
@EntityScan(basePackages = {
    "org.project.easyMGP.models.entity",
    "org.project.framework.models.entity"  
})
@EnableFeignClients(basePackages = {"org.project.easyMGP", "org.project.framework"})
@EnableJpaRepositories(basePackages = {"org.project.easyMGP", "org.project.framework"})
public class EasyMGPApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyMGPApplication.class, args);
	}

}