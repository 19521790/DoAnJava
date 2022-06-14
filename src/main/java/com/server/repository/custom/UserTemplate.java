package com.server.repository.custom;

import com.server.entity.Album;
import com.server.entity.Artist;
import com.server.entity.Song;
import com.server.entity.User;

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
