package com.server.exception;

public class UserException extends Exception {
    private static final long serialVersionUID = 2L;

    public UserException(String message) {
        super(message);
    }

    public static String NotFoundException(String id){return "User with id "+id+" not found!";}

    public static String UserAlreadyExist(){
        return "User already exist!";
    }
}
