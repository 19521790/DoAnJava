package com.server.repository.custom;

import com.server.entity.Artist;
import com.server.entity.Song;
import com.server.entity.result.ArtistResult;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.List;

public interface ArtistTemplate {
    public List<ArtistResult> findAllSongs(String id);
}
