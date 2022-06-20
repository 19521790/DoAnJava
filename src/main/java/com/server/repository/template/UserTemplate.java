package com.server.repository.template;

import com.server.model.Album;
import com.server.model.Artist;
import com.server.model.User;

import java.util.List;

public interface UserTemplate {

    public void addLastListenSong(String idUser, String idSong);
    public User getLastListenSong(String idUser);
    public long countNumberLastListenSong(String idUser);
    public void followArtist(String idUser,String idArtist);
    public void saveAlbum(String idUser, String idAlbum);
    public void unfollowArtist(String idUser,String idArtist);
    public void removeAlbum(String idUser,String idALbum);
    public List<Album> findSavedAlbumFromUser(String idUser);
    public List<Artist> findFollowedArtistFromUser(String idUser);
}
