package org.k8.springcloud.msvc.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcK8CoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcK8CoursesApplication.class, args);
	}

}
