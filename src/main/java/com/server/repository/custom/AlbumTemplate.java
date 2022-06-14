package com.server.repository.custom;

import com.server.entity.Album;

import java.util.List;

public interface AlbumTemplate {
    public List<Album> findByNameRegex(String name);
}
