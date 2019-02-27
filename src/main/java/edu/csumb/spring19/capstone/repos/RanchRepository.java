package edu.csumb.spring19.capstone.repos;

import edu.csumb.spring19.capstone.ranch.RanchData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RanchRepository extends MongoRepository<RanchData, String>{
    
}