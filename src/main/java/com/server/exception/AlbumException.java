package com.server.exception;

public class AlbumException extends Exception{
    private static final long serialVersionUID = 3L;

    public AlbumException(String message){super(message);}

    public static String NotFoundException(String id){
        return "Playlist with id "+id+" not found!";
    }

    public static String AlbumAlreadyExist(){
        return "Album already exist!";
    }
}
