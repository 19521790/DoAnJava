package com.server.repository.template;

import com.server.model.Album;

import java.util.List;

public interface AlbumTemplate {
    public List<Album> findByNameRegex(String name);
}
