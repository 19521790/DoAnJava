package com.server.repository.template;

import com.server.model.Artist;

import java.util.List;

public interface ArtistTemplate {
    public List<Artist> findByNameRegex(String name);
}
