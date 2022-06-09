package com.server.entity.result;

import com.server.entity.Playlist;
import com.server.entity.object.SongOtd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistResult {
    private String id;
    private String name;
    private String image;
    private String idUser;
    private List<SongOtd> songs = new ArrayList<SongOtd>();
    private int totalView;

    public PlaylistResult playlistToPlaylistResult(Playlist playlist){
        PlaylistResult playlistResult = new PlaylistResult();
        playlistResult.setId(playlist.getId());
        playlistResult.setName(playlist.getName());
        playlistResult.setImage(playlist.getImage());
        playlistResult.setIdUser(playlist.getIdUser().toString());
        playlistResult.setSongs(playlist.getSongs());
        playlist.setTotalView(playlist.getTotalView());
        return playlistResult;
    }
}
