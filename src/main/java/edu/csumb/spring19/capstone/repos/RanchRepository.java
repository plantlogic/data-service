package edu.csumb.spring19.capstone.repos;

import edu.csumb.spring19.capstone.models.card.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public interface RanchRepository extends MongoRepository<Card, String>{
    List<Card> findByRanchName(String ranchName);
    List<Card> findByRanchManagerName(String ranchManagerName);
    Iterable<Card> findAllByIsClosedTrue(Sort sort);
    Iterable<Card> findAllByIsClosedFalse(Sort sort);
    List<Card> findByFieldID(Integer fieldID);
}

