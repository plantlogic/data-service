package edu.csumb.spring19.capstone.repos;

import edu.csumb.spring19.capstone.models.export.ExportPreset;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportPresetRepository extends MongoRepository<ExportPreset, String> {

}
