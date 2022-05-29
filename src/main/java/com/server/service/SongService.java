package com.server.service;

import com.server.entity.Song;
import com.server.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public List<Song> addSongs(List<Song> songs) {
        return songRepository.saveAll(songs);
    }

    public Song findSongById(String id) {
        System.out.println(songRepository.findById(id).orElse(null));
        return songRepository.findById(id).orElse(null);
    }

    public List<Song> findAllSongs() {
        return songRepository.findAll();
    }

    public String deleteSong(String id) {
        try{
            songRepository.deleteById(id);
            return "Song has been removed: " + id;
        }catch(Exception e) {
            return e.getMessage();
        }
    }

    public Song updateSong(Song song) {
        Song songToUpdate = songRepository.findById(song.getId()).get();
        if(songToUpdate!=null){
            songToUpdate.setName(song.getName()!=null?song.getName():songToUpdate.getName());
            songToUpdate.setImage(song.getImage()!=null?song.getImage():songToUpdate.getImage());
            songToUpdate.setDuration(song.getDuration());
            songToUpdate.setWeekView(song.getWeekView());
            songToUpdate.setTotalView(song.getTotalView());
            return songRepository.save(songToUpdate);
        }else{
            return songToUpdate;
        }
    }
}
