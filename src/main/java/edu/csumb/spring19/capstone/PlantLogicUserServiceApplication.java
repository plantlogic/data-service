package edu.csumb.spring19.capstone;

import edu.csumb.spring19.capstone.models.common.CommonInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlantLogicUserServiceApplication implements CommandLineRunner {
	@Autowired
	private CommonInitService commonInitService;

	public static void main(String[] args) {
		SpringApplication.run(PlantLogicUserServiceApplication.class, args);
	}

	@Override
	public void run(String... params) {
		commonInitService.initDatabase();
	}
}
