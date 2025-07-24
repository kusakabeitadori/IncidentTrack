package tech.noji.IncidentTrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("tech.noji.IncidentTrack.entite")
@EnableJpaRepositories("tech.noji.IncidentTrack.repository")
public class IncidentTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidentTrackApplication.class, args);
	}

}




