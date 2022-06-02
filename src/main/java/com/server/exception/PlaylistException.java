package com.server.exception;

public class PlaylistException extends Exception{
    private static final long serialVersionUID = 3L;

    public PlaylistException(String message){super(message);}

    public static String NotFoundException(String id){
        return "Playlist with id "+id+" not found!";
    }

    public static String PlaylistALreadyExist(){
        return "Playlist already exist!";
    }
}
