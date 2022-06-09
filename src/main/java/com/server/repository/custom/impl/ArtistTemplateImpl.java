package com.server.repository.custom.impl;

import com.server.entity.Artist;
import com.server.entity.Song;
import com.server.entity.result.ArtistResult;
import com.server.repository.custom.ArtistTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

public class ArtistTemplateImpl implements ArtistTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ArtistResult> findAllSongs(String idArtist){
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("songs")
                .localField("idSongs")
                .foreignField("_id")
                .as("songs");
        Aggregation aggregation = Aggregation.newAggregation(
                lookupOperation
        );
        AggregationResults<ArtistResult> results = this.mongoTemplate.aggregate(aggregation,"artist",ArtistResult.class);
        System.out.println(results);
        return results.getMappedResults();
    }
}
