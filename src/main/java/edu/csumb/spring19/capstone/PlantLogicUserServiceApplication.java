package edu.csumb.spring19.capstone;

import edu.csumb.spring19.capstone.models.common.CommonInitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlantLogicUserServiceApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(PlantLogicUserServiceApplication.class, args);
	}

	@Override
	public void run(String... params) {
		CommonInitService.initDatabase();
	}
}
