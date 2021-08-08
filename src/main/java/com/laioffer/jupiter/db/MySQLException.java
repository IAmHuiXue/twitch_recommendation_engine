package com.laioffer.jupiter.db;

public class MySQLException extends RuntimeException {
    // We’ll throw this exception if there is something wrong when connecting to MySQL.
    public MySQLException(String errorMessage) {
        super(errorMessage);
    }
}
