package edu.csumb.spring19.capstone.repos;

import edu.csumb.spring19.capstone.models.card.Card;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
// github.com/spring-projects/spring-data-mongodb/blob/master/src/main/asciidoc/reference/mongo-repositories.adoc

@Repository
public interface RanchRepository extends MongoRepository<Card, String>{
    List<Card> findByRanchName(String ranchName);
    List<Card> findByRanchManagerName(String ranchManagerName);
    Iterable<Card> findAllByIsClosedTrue(Sort sort);
    Iterable<Card> findAllByIsClosedFalse(Sort sort);
    Iterable<Card> findAllByRanchNameIsIn(List<String> ranchNames, Sort sort);
    Iterable<Card> findAllByRanchNameIsIn(List<String> ranchNames);
    Iterable<Card> findAllByShippersContaining(String shipperID, Sort sort);
    Iterable<Card> findAllByIsClosedFalseAndRanchNameIsIn(List<String> ranchNames, Sort sort);
    Iterable<Card> findAllByIsClosedTrueAndRanchNameIsIn(List<String> ranchNames, Sort sort);
    List<Card> findByFieldID(Integer fieldID);
    
    @Query(value = "{'ranchName': {'$in': ?0}, 'shippers': {'$in': [?1]}}")
    Iterable<Card> findAllByRanchNameIsInAndShippersContaining(List<String> ranchNames, String shipperID, Sort sort);
}

