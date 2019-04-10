package edu.csumb.spring19.capstone.repos;

import edu.csumb.spring19.capstone.models.RanchData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public interface RanchRepository extends MongoRepository<RanchData, String>{
    List<RanchData> findByRanchName(String ranchName);
    List<RanchData> findByRanchManagerName(String ranchManagerName);
    Iterable<RanchData> findAllByIsClosed(Boolean isClosed, Sort sort);
}

