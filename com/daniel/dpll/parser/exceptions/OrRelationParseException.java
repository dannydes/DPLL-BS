package com.daniel.dpll.parser.exceptions;

/**
 * Exception class for clause parse errors
 * @author Daniel
 */
public class OrRelationParseException extends Exception {
    /**
     * Exception constructor
     */
    public OrRelationParseException() {
        super("Incorrect clause format!");
    }
}
