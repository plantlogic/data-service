package edu.csumb.spring19.capstone.models.common;

import edu.csumb.spring19.capstone.repos.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonInitService {
    @Autowired
    private CommonRepository commonRepository;

    public void initDatabase() {
        if (!commonRepository.existsById("ranches")) {
            commonRepository.saveAll((new CommonInitBuilder()).ranches().build());
        }

        if (!commonRepository.existsById("fertilizers")) {
            commonRepository.saveAll((new CommonInitBuilder()).fertilizers().build());
        }

        if (!commonRepository.existsById("chemicals")) {
            commonRepository.saveAll((new CommonInitBuilder()).chemicals().build());
        }

        if (!commonRepository.existsById("chemicalRateUnits")) {
            commonRepository.saveAll((new CommonInitBuilder()).chemicalRateUnits().build());
        }

        if (!commonRepository.existsById("irrigationMethod")) {
            commonRepository.saveAll((new CommonInitBuilder()).irrigationMethod().build());
        }

        if (!commonRepository.existsById("irrigators")) {
            commonRepository.saveAll((new CommonInitBuilder()).irrigators().build());
        }

        if (!commonRepository.existsById("tractorOperators")) {
            commonRepository.saveAll((new CommonInitBuilder()).tractorOperators().build());
        }

        if (!commonRepository.existsById("tractorWork")) {
            commonRepository.saveAll((new CommonInitBuilder()).tractorWork().build());
        }

        if (!commonRepository.existsById("bedTypes")) {
            commonRepository.saveAll((new CommonInitBuilder()).bedTypes().build());
        }

        if (commonRepository.existsById("bedCounts")) {
            commonRepository.deleteById("bedCounts");
        }

        if (!commonRepository.existsById("commodities")) {
            commonRepository.saveAll((new CommonInitBuilder()).commodities().build());
        }
    }
}
