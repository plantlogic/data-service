package edu.csumb.spring19.capstone;

import edu.csumb.spring19.capstone.models.common.CommonInit;
import edu.csumb.spring19.capstone.repos.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlantLogicUserServiceApplication implements CommandLineRunner {
	@Autowired
	private CommonRepository commonRepository;

	public static void main(String[] args) {
		SpringApplication.run(PlantLogicUserServiceApplication.class, args);
	}

	@Override
	public void run(String... params) {
		if (!commonRepository.existsById("ranches")) {
			commonRepository.saveAll((new CommonInit()).ranches().build());
		}

		if (!commonRepository.existsById("fertilizers")) {
			commonRepository.saveAll((new CommonInit()).fertilizers().build());
		}

		if (!commonRepository.existsById("chemicals")) {
			commonRepository.saveAll((new CommonInit()).chemicals().build());
		}

		if (!commonRepository.existsById("tractorOperators")) {
			commonRepository.saveAll((new CommonInit()).tractorOperators().build());
		}

		if (!commonRepository.existsById("bedTypes")) {
			commonRepository.saveAll((new CommonInit()).bedTypes().build());
		}

		if (!commonRepository.existsById("bedCounts")) {
			commonRepository.saveAll((new CommonInit()).bedCounts().build());
		}

		if (!commonRepository.existsById("commodities")) {
			commonRepository.saveAll((new CommonInit()).commodities().build());
		}
	}
}
