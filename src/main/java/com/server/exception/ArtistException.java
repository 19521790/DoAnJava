package com.server.exception;

public class ArtistException extends Exception{
    private static final long serialVersionUID = 5L;

    public ArtistException(String message){super(message);}

    public static String NotFoundException(String id){
        return "Artist with id "+id+" not found!";
    }

    public static String ArtistAlreadyExist(String name){
        return "Artist with name " + name +" already exist!";
    }

}
