package com.daniel.dpll.algo.ds;

public class Variable {

    private final char variable;
    private boolean value;
    private boolean assigned;
    
    private boolean pure;
    
    //Should be set only in case var is pure
    private boolean not;

    public Variable(String variable) {
        this.variable = variable.charAt(0);
        assigned = false;
    }

    public Variable(String variable, boolean value) {
        this.variable = variable.charAt(0);
        this.value = value;
        assigned = true;
    }

    public char getVariable() {
        return variable;
    }

    public void setValue(boolean value) {
        this.value = value;
        assigned = true;
    }

    public boolean getValue() {
        return value;
    }
    
    public void markPure() {
        pure = true;
    }
    
    public boolean isPure() {
        return pure;
    }
    
    public void setNot(boolean not) {
        this.not = not;
    }
    
    public boolean isNot() {
        return not;
    }

    public boolean isUnassigned() {
        return !assigned;
    }

    public boolean isAssigned() {
        return assigned;
    }

    @Override
    public String toString() {
        return Character.toString(variable) + ": " + Boolean.toString(value);
    }
}
