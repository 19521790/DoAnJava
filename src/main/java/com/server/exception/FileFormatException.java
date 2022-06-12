package com.server.exception;

public class FileFormatException extends Exception {
    private static final long serialVersionUID = 7L;

    public FileFormatException(String message) {
        super(message);
    }

    public static String NotFoundException(String format) {
        return "Format not found: " + format;
    }

}
