package com.daniel.dpll.algo;

import com.daniel.dpll.algo.ds.*;

import com.daniel.dpll.parser.Parser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;

public class DPLL {

    private static DPLL instance = null;

    private ArrayList<Variable> variables;
    private Variable current;
    private ListIterator iterator;
    private boolean satisfiable;

    private DPLL(Parser parser) {
        variables = parser.getVars();
        
        iterator = variables.listIterator();
        current = null;
        
        satisfiable = dpll(parser.getAndRel());
    }

    public static DPLL createInstance(Parser parser) {
        if (instance == null) {
            instance = new DPLL(parser);
        }
        return instance;
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

            for (OrRelation clause : clauses) {
                HashSet<NotRelation> literals = clause.getNotRelations();
                boolean firstLiteral = true;
                boolean not = false;
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
            NotRelation l = new NotRelation(variables, var.toString(), false);
            OrRelation clause = new OrRelation();
            clause.getNotRelations().add(l);
            return clause;
        }
        return null;
    }

    private AndRelation notClause(AndRelation formula, OrRelation l) {
        HashSet<NotRelation> literals = l.getNotRelations();

        //Supposed to run only once
        for (NotRelation literal : literals) {
            literal.not();
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
        formula.getOrRelations().add(l);

        return dpll(formula) || dpll(notClause(formula, l));
    }

    public boolean isSatisfiable() {
        return satisfiable;
    }
}
