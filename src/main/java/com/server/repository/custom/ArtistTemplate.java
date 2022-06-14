package com.server.repository.custom;

import com.server.entity.Artist;

import java.util.List;

public interface ArtistTemplate {
    public List<Artist> findByNameRegex(String name);
}
