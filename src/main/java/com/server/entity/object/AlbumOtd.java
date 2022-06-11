package com.server.entity.object;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.entity.Album;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumOtd {
    @Id
    private String id;

    private String name;
    private String image;

    public AlbumOtd albumToAlbumOtd(Album album){
        AlbumOtd albumOtd = new AlbumOtd();
        albumOtd.setId(album.getId());
        albumOtd.setName(album.getName());
        albumOtd.setImage(album.getImage());
        return albumOtd;
    }
}
