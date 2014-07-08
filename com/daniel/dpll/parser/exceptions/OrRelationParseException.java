/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.daniel.dpll.parser.exceptions;

/**
 *
 * @author Daniel
 */
public class OrRelationParseException extends Exception {
    public OrRelationParseException() {
        super("Incorrect clause format!");
    }
}
