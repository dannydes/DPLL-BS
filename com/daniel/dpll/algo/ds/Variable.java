package com.daniel.dpll.algo.ds;

public class Variable {

    private final char variable;
    private boolean value;
    private boolean assigned;

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
