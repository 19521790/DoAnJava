package com.server.repository.custom;

import com.server.entity.User;

public interface UserTemplate {
    public void addPlaylistToUser(String idUser, String idPlaylist);
    public User findUserById(String idUser);
    public void addLastListenSong(String idUser, String idSong);
}
