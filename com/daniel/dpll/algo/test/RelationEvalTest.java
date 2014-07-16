package com.daniel.dpll.algo.test;

import com.daniel.dpll.algo.ds.AndRelation;
import com.daniel.dpll.algo.ds.NotRelation;
import com.daniel.dpll.algo.ds.OrRelation;
import com.daniel.dpll.algo.ds.Variable;
import java.util.ArrayList;
import org.junit.Assert;

/**
 * Class for relation evaluation tests
 * @author Daniel
 */
public class RelationEvalTest extends Assert {
    
    /**
     * Test for not relation evaluation
     */
    public static void not() {
        ArrayList<Variable> variables = new ArrayList();
        variables.add(new Variable("a"));
        NotRelation l = new NotRelation(variables, "a", true);
        assertTrue(l.eval());
    }
    
    /**
     * Few tests for or relation evaluation
     */
    public static void or() {
        ArrayList<Variable> variables = new ArrayList();
        variables.add(new Variable("a", true));
        variables.add(new Variable("b"));
        variables.add(new Variable("c"));
        
        OrRelation clause = new OrRelation();
        
        NotRelation a = new NotRelation(variables, "a", false);
        
        clause.getNotRelations().add(a);
        clause.getNotRelations().add(new NotRelation(variables, "b", false));
        clause.getNotRelations().add(new NotRelation(variables, "c", false));
        assertTrue(clause.eval());
        
        a.not();
        assertFalse(clause.eval());
    }
    
    /**
     * Few tests for and relation evaluation
     */
    public static void and() {
        ArrayList<Variable> variables = new ArrayList();
        variables.add(new Variable("a", true));
        variables.add(new Variable("b"));
        variables.add(new Variable("c"));
        
        AndRelation formula = new AndRelation();
        
        OrRelation clause1 = new OrRelation();
        clause1.getNotRelations().add(new NotRelation(variables, "a", false));
        clause1.getNotRelations().add(new NotRelation(variables, "b", false));
        
        OrRelation clause2 = new OrRelation();
        clause2.getNotRelations().add(new NotRelation(variables, "c", true));
        
        formula.getOrRelations().add(clause1);
        formula.getOrRelations().add(clause2);
        
        assertTrue(formula.eval());
    }
    
}
