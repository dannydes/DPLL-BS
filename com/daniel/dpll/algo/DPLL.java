package com.daniel.dpll.algo;

import com.daniel.dpll.algo.ds.*;

import com.daniel.dpll.parser.Parser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;

public class DPLL {

    private final ArrayList<Variable> variables;
    private final ListIterator iterator;
    private final boolean satisfiable;

    public DPLL(Parser parser) {
        variables = parser.getVars();
        iterator = variables.listIterator();        
        satisfiable = dpll(parser.getAndRel());
    }

    private boolean consistent(AndRelation formula) {
        return formula.eval();
    }

    private boolean emptyClause(AndRelation formula) {
        for (Variable literal : variables) {
            if (literal.isUnassigned()) {
                return false;
            }
        }
        return !formula.eval();
    }

    private void eliminatePureLiterals(AndRelation formula) {
        for (Variable variable : variables) {
            HashSet<NotRelation> pureLiterals = new HashSet();
            
            HashSet<OrRelation> clauses = formula.getOrRelations();
            boolean pure = true;
            boolean firstLiteral = true;
            boolean not = false;

            for (OrRelation clause : clauses) {
                HashSet<NotRelation> literals = clause.getNotRelations();
                for (NotRelation literal : literals) {
                    if (literal.getVariable() == variable) {
                        if (firstLiteral) {
                            not = literal.getNot();
                            firstLiteral = false;
                        }
                        
                        if (not != literal.getNot()) {
                            pure = false;
                            break;
                        }
                        
                        //Assuming literals are pure
                        pureLiterals.add(literal);
                    }
                }
                if (!pure) {
                    break;
                }
            }

            if (pure) {
                for (NotRelation literal : pureLiterals) {
                    for (OrRelation clause : clauses) {
                        clause.getNotRelations().remove(literal);
                    }
                }
            }
        }
    }

    private void propagateUnits(AndRelation formula) {
        for (OrRelation clause : formula.getOrRelations()) {
            if (clause.isUnit()) {
                clause.getLastUnassigned().setValue(true);
            }
        }
    }

    private OrRelation chooseLiteral() {
        if (iterator.hasNext()) {
            Variable var = (Variable) iterator.next();
            var.setValue(true);
            NotRelation l = new NotRelation(variables, var.toString(), false);
            OrRelation clause = new OrRelation();
            clause.getNotRelations().add(l);
            return clause;
        }
        return null;
    }

    private AndRelation notClause(AndRelation formula, OrRelation l) {
        if (l != null) {
            HashSet<NotRelation> literals = l.getNotRelations();

            //Supposed to run only once
            for (NotRelation literal : literals) {
                literal.getVariable().setValue(false);
            }
        }

        return formula;
    }

    private boolean dpll(AndRelation formula) {
        if (consistent(formula)) {
            return true;
        }

        if (emptyClause(formula)) {
            return false;
        }

        propagateUnits(formula);

        eliminatePureLiterals(formula);

        OrRelation l = chooseLiteral();
        if (l != null) {
            formula.getOrRelations().add(l);
        }

        return dpll(formula) || dpll(notClause(formula, l));
    }

    public boolean isSatisfiable() {
        return satisfiable;
    }
    
    public ArrayList<Variable> getVariables() {
        return variables;
    }
    
}
