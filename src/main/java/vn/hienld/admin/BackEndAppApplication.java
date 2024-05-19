package vn.hienld.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BackEndAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndAppApplication.class, args);
	}

}
