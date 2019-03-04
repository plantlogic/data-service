package edu.csumb.spring19.capstone.repos;

import edu.csumb.spring19.capstone.models.TractorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TractorRepository extends MongoRepository<TractorData, String>{
    
}