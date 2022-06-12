package com.server.repository.custom;

import com.server.entity.Song;
import com.server.entity.User;

import java.util.List;

public interface UserTemplate {
    public void addPlaylistToUser(String idUser, String idPlaylist);
    public User findUserById(String idUser);
    public void addLastListenSong(String idUser, String idSong);
    public User getLastListenSong(String idUser);
    public long countNumberLastListenSong(String idUser);
}
