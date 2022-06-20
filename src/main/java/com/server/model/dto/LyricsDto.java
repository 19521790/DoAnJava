package com.server.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.model.LyricTimeStamp;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LyricsDto {
    private String id;
    private String idSong;
    private List<LyricTimeStamp> lyrics;
}
