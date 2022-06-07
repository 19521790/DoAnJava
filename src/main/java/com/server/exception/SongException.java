package com.server.exception;

public class SongException extends Exception {

    private static final long serialVersionUID = 1L;

    public SongException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Song with id " + id + " not found!";
    }

    public static String SongAlreadyExist(String name) {
        return "Song with name " + name + " already exists!";
    }
}
