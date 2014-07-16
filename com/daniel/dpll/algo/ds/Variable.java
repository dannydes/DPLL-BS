package com.daniel.dpll.algo.ds;

/**
 * Class representing a variable
 * @author Daniel
 */
public class Variable {

    /**
     * Variable letter
     */
    private final char variable;
    
    /**
     * Value assigned. Always false for unassigned.
     */
    private boolean value;
    
    /**
     * Whether variable is assigned
     */
    private boolean assigned;
    
    /**
     * Whether variable always occurs with same polarity
     */
    private boolean pure;
    
    /**
     * Whether variable always occurs with a not. Should be set only in case variable is pure.
     */
    private boolean not;

    /**
     * Constructor
     * @param variable string containing variable name
     */
    public Variable(String variable) {
        this.variable = variable.charAt(0);
        assigned = false;
    }

    /**
     * Constructor that pre-assigns value
     * @param variable string containing variable name
     * @param value value to be assigned
     */
    public Variable(String variable, boolean value) {
        this.variable = variable.charAt(0);
        this.value = value;
        assigned = true;
    }

    /**
     * Getter for variable letter
     * @return variable letter
     */
    public char getVariable() {
        return variable;
    }

    /**
     * Setter for value
     * @param value value to be assigned
     */
    public void setValue(boolean value) {
        this.value = value;
        assigned = true;
    }

    /**
     * Getter for value
     * @return value assigned
     */
    public boolean getValue() {
        return value;
    }
    
    /**
     * Marks variable as pure
     */
    public void markPure() {
        pure = true;
    }
    
    /**
     * Getter for pure
     * @return whether variable always occurs with same polarity
     */
    public boolean isPure() {
        return pure;
    }
    
    /**
     * Setter for not, requiring purity check to be done outside
     * @param not whether variable always occurs with a not
     */
    public void setNot(boolean not) {
        this.not = not;
    }
    
    /**
     * Getter for not
     * @return whether variable always occurs with a not
     */
    public boolean isNot() {
        return not;
    }

    /**
     * Reports whether unassigned
     * @return inverted value of assigned
     */
    public boolean isUnassigned() {
        return !assigned;
    }

    /**
     * Getter for assigned
     * @return whether variable is assigned
     */
    public boolean isAssigned() {
        return assigned;
    }

    /**
     * Gives a string representation to variable
     * @return string representation
     */
    @Override
    public String toString() {
        return Character.toString(variable) + ": " + Boolean.toString(value);
    }
}
