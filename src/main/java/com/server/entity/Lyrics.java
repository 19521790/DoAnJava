package com.server.entity;

import com.server.entity.dto.LyricDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "lyrics")
public class Lyrics {
    @Id
    private String id;
    private String idSong;
    private List<LyricDto> lyrics;
}
