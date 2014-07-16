package com.daniel.dpll.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.daniel.dpll.algo.ds.AndRelation;
import com.daniel.dpll.algo.ds.Variable;

/**
 * Class driving CNF parsing
 * @author Daniel
 */
public class Parser {

    /**
     * Singleton Parser instance
     */
    private static Parser instance = null;

    /**
     * List of variables
     */
    private ArrayList<Variable> variables;
    
    /**
     * Formula
     */
    private AndRelation andRelation;

    /**
     * Constructor
     * @param formula string representing formula
     * @throws Exception 
     */
    private Parser(String formula) throws Exception {
        modInstance(formula);
    }

    /**
     * Modifies singleton instance fields
     * @param formula string representing formula
     * @throws Exception 
     */
    private void modInstance(String formula) throws Exception {
        variables = new ArrayList<Variable>();
        andRelation = null;

        setVars(formula);
        setAndRel(formula);
    }

    /**
     * Creates singleton instance
     * @param formula string representing formula
     * @return singleton Parser instance
     * @throws Exception 
     */
    public static Parser createInstance(String formula) throws Exception {
        if (instance == null) {
            instance = new Parser(formula);
        } else {
            instance.modInstance(formula);
        }

        return instance;
    }

    /**
     * Populates variables ArrayList through the formula string
     * @param formula string representing formula
     */
    private void setVars(String formula) {
        Pattern varPattern = Pattern.compile("(\\w)");
        Matcher matcher = varPattern.matcher(formula);

        while (matcher.find()) {
            String match = matcher.group();
            if (!varExists(match.charAt(0))) {
                variables.add(new Variable(match));
            }
        }
    }

    /**
     * Checks if variable has already been added to the variables ArrayList
     * @param var variable letter
     * @return whether variable is found
     */
    private boolean varExists(char var) {
        for (Variable variable : variables) {
            if (variable.getVariable() == var) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for variables
     * @return list of variables
     */
    public ArrayList<Variable> getVars() {
        return variables;
    }

    /**
     * Creates an object-based representation of the formula
     * @param formula string representing formula
     * @throws Exception 
     */
    private void setAndRel(String formula) throws Exception {
        andRelation = AndRelation.parse(formula, variables);
    }

    /**
     * Getter for andRelation
     * @return formula
     */
    public AndRelation getAndRel() {
        return andRelation;
    }
}
