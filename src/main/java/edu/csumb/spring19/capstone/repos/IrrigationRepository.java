package edu.csumb.spring19.capstone.repos;

import edu.csumb.spring19.capstone.models.IrrigationData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrrigationRepository extends MongoRepository<IrrigationData, String>{
    
}