package com.daniel.dpll.algo.ds;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing a literal
 * @author Daniel
 */
public class NotRelation extends Relation {

    /**
     * Variable instance to which the literal belongs
     */
    private Variable variable;
    
    /**
     * Whether the literal has a not
     */
    private boolean not;

    /**
     * Constructor
     * @param variables list of variables
     * @param variable string containing variable name
     * @param not whether the literal has a not
     */
    public NotRelation(ArrayList<Variable> variables, String variable, boolean not) {
        linkVariable(variables, variable);
        this.not = not;
    }

    /**
     * Connects the literal to an existing variable
     * @param variables list of variables
     * @param variable string containing variable name
     */
    private void linkVariable(ArrayList<Variable> variables, String variable) {
        char v = variable.charAt(0);
        for (Variable var : variables) {
            if (var.getVariable() == v) {
                this.variable = var;
                return;
            }
        }
    }

    /**
     * Getter for variable
     * @return Variable instance to which the literal belongs
     */
    public final Variable getVariable() {
        return variable;
    }

    /**
     * Getter for not
     * @return Whether the literal has a not
     */
    public final boolean getNot() {
        return not;
    }
    
    /**
     * Not inverter
     */
    public final void not() {
        not = !not;
    }

    /**
     * Evaluates literal based on assigned value and whether it has a not
     * @return literal value
     */
    @Override
    public boolean eval() {
        return not ? !variable.getValue() : variable.getValue();
    }

    /**
     * Parses a literal substring within a clause substring
     * @param varString literal substring
     * @param variables list of variables
     * @return NotRelation instance representing parsed substring
     * @throws Exception
     */
    public static NotRelation parse(String varString, ArrayList<Variable> variables) throws Exception {
        Pattern notPattern = Pattern.compile("!");
        Matcher matcher = notPattern.matcher(varString);
        boolean not = matcher.find();

        Pattern varPattern = Pattern.compile("\\w");
        matcher = varPattern.matcher(varString);
        if (!matcher.find()) {
            throw new Exception("Missing variable literal!");
        }
        String var = matcher.group();

        return new NotRelation(variables, var, not);
    }
    
}
