package com.server.exception;

public class GenreException extends Exception {
    private static final long serialVersionUID = 6L;

    public GenreException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Genre with id " + id + " not found!";
    }

    public static String GenreAlreadyExist(String name) {
        return "Genre with name " + name + " already exist!";
    }
}
