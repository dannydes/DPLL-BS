package com.daniel.dpll.algo;

import com.daniel.dpll.algo.ds.*;

import com.daniel.dpll.parser.Parser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;

/**
 * Class containing most DPLL routines
 * @author Daniel
 */
public class DPLL {

    /**
     * List of variables
     */
    private final ArrayList<Variable> variables;
    
    /**
     * Iterator for variables
     */
    private final ListIterator iterator;
    
    /**
     * Satisfiability of formula
     */
    private final boolean satisfiable;

    /**
     * Constructor, initializing DPLL algorithm
     * @param parser Parser instance
     */
    public DPLL(Parser parser) {
        variables = parser.getVars();
        iterator = variables.listIterator();        
        satisfiable = dpll(parser.getAndRel());
    }

    /**
     * Checks that all clauses are true
     * @param formula set of clauses
     * @return whether clauses are consistent
     */
    private boolean consistent(AndRelation formula) {
        return formula.eval();
    }

    /**
     * Checks whether all literals are assigned and if any clause is false
     * @param formula set of clauses
     * @return presence of empty clauses
     */
    private boolean emptyClause(AndRelation formula) {
        for (Variable literal : variables) {
            if (literal.isUnassigned()) {
                return false;
            }
        }
        return !formula.eval();
    }

    /**
     * Eliminates literals which always occur with the same polarity within the formula
     * @param formula set of clauses
     */
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
                
                //For more intelligent literal/value choice
                variable.setValue(!not);
                variable.markPure();
                variable.setNot(not);
            }
        }
    }

    /**
     * Assigns unassigned literals in unit clauses to true
     * @param formula set of clauses
     */
    private void propagateUnits(AndRelation formula) {
        for (OrRelation clause : formula.getOrRelations()) {
            if (clause.isUnit()) {
                clause.getLastUnassigned().setValue(true);
            }
        }
    }

    /**
     * Chooses the next variable and assigns it to true
     * @return wrapper for next variable's literal
     */
    private OrRelation chooseLiteral() {
        if (iterator.hasNext()) {
            Variable var = (Variable) iterator.next();
            
            NotRelation l;
            if (var.isPure() && var.isNot()) {
                var.setValue(false);
                l = new NotRelation(variables, var.toString(), true);
            } else {
                var.setValue(true);
                l = new NotRelation(variables, var.toString(), false);
            }
            
            OrRelation clause = new OrRelation();
            clause.getNotRelations().add(l);
            return clause;
        }
        return null;
    }

    /**
     * Assigns the opposite polarity to the chosen variable
     * @param formula set of clauses
     * @param l wrapper for chosen literal
     * @return wrapper for the chosen variable literal given the opposite polarity
     */
    private AndRelation notClause(AndRelation formula, OrRelation l) {
        if (l != null) {
            HashSet<NotRelation> literals = l.getNotRelations();

            //Supposed to run only once
            for (NotRelation literal : literals) {
                Variable var = literal.getVariable();
                var.setValue(!var.getValue());
                
                if (var.isPure() && var.isNot()) {
                    var.setValue(false);
                    literal.not();
                }
            }
        }

        return formula;
    }

    /**
     * Main entrance to the DPLL algorithm
     * @param formula set of clauses
     * @return satisfiability of formula
     */
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

    /**
     * Getter for satisfiable
     * @return satisfiable
     */
    public boolean isSatisfiable() {
        return satisfiable;
    }
    
    /**
     * Getter for variables
     * @return list of variables
     */
    public ArrayList<Variable> getVariables() {
        return variables;
    }
    
}
