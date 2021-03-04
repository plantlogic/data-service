package edu.csumb.spring19.capstone.repos;

import edu.csumb.spring19.capstone.models.card.Card;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
// github.com/spring-projects/spring-data-mongodb/blob/master/src/main/asciidoc/reference/mongo-repositories.adoc

@Repository
public interface RanchRepository extends MongoRepository<Card, String>{
    List<Card> findAll();
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

    @Query(value="{'ranchName': {'$in': ?0}}", fields = "{ 'ranchName': 1 }")
    Iterable<Card> findAllRanchIds(List<String> ranchNames);

    @Query(value = "{'ranchName': {'$in': ?0}, 'isClosed': ?1}", fields = "{ 'ranchName': 1 }")
    Iterable<Card> findAllRanchIds(List<String> ranchNames, boolean isClosed);

    @Query(value="{'ranchName': {'$in': ?0}}", fields = "{ 'commodities': 1 }")
    Iterable<Card> findAllCommodityIds(List<String> ranchNames);

    @Query(value = "{'ranchName': {'$in': ?0}, 'isClosed': ?1}", fields = "{ 'commodities': 1 }")
    Iterable<Card> findAllCommodityIds(List<String> ranchNames, boolean isClosed);
    
    @Query(value = "{'shippers': {'$in': [?0]}, 'isClosed': ?1}", fields = "{ 'commodities': 1 }")
    Iterable<Card> findAllCommodityIdsByShipper(String shipperID, boolean isClosed);

    @Query(value="{'$and': [{'harvestDate': {'$gte' : ?0}},{'harvestDate': {'$lte' : ?1}}]}", fields ="{'harvestDate': 1}")
    Iterable<Card> findAllHarvestedBetween(Date from, Date to);
    
    @Query(value="{'shippers': {'$in': [?0]}, '$and': [{'harvestDate': {'$gte' : ?1}},{'harvestDate': {'$lte' : ?2}}]}",
           fields ="{'harvestDate': 1}")
    Iterable<Card> findAllByShipperAndHarvestedBetween(String shipperID, Date from, Date to);

    @Query(value="{'ranchName': {'$in': ?0}}}", count = true)
    Long countAllByRanchNameIsIn(List<String> ranchNames);
    
    @Query(value="{'ranchName': {'$in': ?0}, 'isClosed': ?1}}", count = true)
    Long countAllByRanchNameIsIn(List<String> ranchNames, boolean isClosed);

    @Query(value="{'shippers': {'$in': [?0]}}", count = true)
    Long countAllByShippersContaining(String shipperID);
    
    @Query(value="{'shippers': {'$in': [?0]}, 'isClosed': ?1}}", count = true)
    Long countAllByShippersContaining(String shipperID, boolean isClosed);
}

