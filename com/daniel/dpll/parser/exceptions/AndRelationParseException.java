/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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