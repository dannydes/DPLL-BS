package com.daniel.dpll.algo.ds;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotRelation extends Relation {

    private Variable variable;
    private boolean not;

    public NotRelation(ArrayList<Variable> variables, String variable, boolean not) {
        linkVariable(variables, variable);
        this.not = not;
    }

    private void linkVariable(ArrayList<Variable> variables, String variable) {
        char v = variable.charAt(0);
        for (Variable var : variables) {
            if (var.getVariable() == v) {
                this.variable = var;
                return;
            }
        }
    }

    public final Variable getVariable() {
        return variable;
    }

    public final boolean getNot() {
        return not;
    }

    public final void not() {
        not = !not;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean eval() {
        return not ? !variable.getValue() : variable.getValue();
    }

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
