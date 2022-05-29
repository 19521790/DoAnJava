package com.server.repository;


import com.server.entity.Chart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartRepository extends MongoRepository<Chart,String> {
}
