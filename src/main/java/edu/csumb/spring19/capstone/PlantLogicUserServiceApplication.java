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
	public void run(String... params) throws Exception {
		if (commonRepository.count() < 1) {
			commonRepository.saveAll(
				(new CommonInit())
					.ranches()
					.fertilizers()
					.chemicals()
					.tractorOperators()
					.bedTypes()
					.bedCounts()
					.commodities()
				.build()
			);
		}
	}
}
