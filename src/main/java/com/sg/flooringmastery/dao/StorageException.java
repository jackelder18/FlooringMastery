package com.sg.flooringmastery.dao;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class StorageException extends Exception {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable innerEx) {
        super(message, innerEx);
    }
}
