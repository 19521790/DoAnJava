package com.example.doanjavaserver.repository;


import com.example.doanjavaserver.entity.Chart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartRepository extends MongoRepository<Chart,String> {
}
