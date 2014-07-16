package com.daniel.dpll.algo.ds;

import java.util.ArrayList;

/**
 * Class acting as a backbone for relations
 * @author Daniel
 */
public abstract class Relation {

    /**
     * Evaluates some relation type
     * @return boolean value of relation
     */
    public abstract boolean eval();

    /**
     * Converts a string into an object-based representation of that relation
     * @param segment string or substring to be parsed
     * @return an object-based representation of that relation
     * @throws Exception
     */
    public static Relation parse(String segment, ArrayList<Variable> variables) throws Exception {
        return null;
    }
}
