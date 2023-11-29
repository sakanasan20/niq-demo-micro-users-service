package tw.niq.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient

@SpringBootApplication
public class NiqDemoMicroUsersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NiqDemoMicroUsersServiceApplication.class, args);
	}

}
