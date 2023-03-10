package cat.tecnocampus.recommendations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RecommendationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendationsApplication.class, args);
	}

}
