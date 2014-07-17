package com.daniel.dpll.parser.exceptions;

/**
 * Exception class for formula parse errors
 * @author Daniel
 */
public class AndRelationParseException extends Exception {
    /**
     * Exception constructor
     */
    public AndRelationParseException() {
        super("Incorrect formula format!");
    }
}
