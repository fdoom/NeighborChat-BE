package kit.hackathon.nearbysns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NearbysnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearbysnsApplication.class, args);
	}

}
