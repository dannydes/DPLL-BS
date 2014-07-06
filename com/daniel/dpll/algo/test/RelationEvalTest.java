package com.daniel.dpll.algo.test;

import com.daniel.dpll.algo.ds.NotRelation;
import com.daniel.dpll.algo.ds.OrRelation;
import com.daniel.dpll.algo.ds.Variable;
import java.util.ArrayList;
import org.junit.Assert;

/**
 *
 * @author Daniel
 */
public class RelationEvalTest extends Assert {
    public static void not() {
        ArrayList<Variable> variables = new ArrayList();
        variables.add(new Variable("a"));
        NotRelation l = new NotRelation(variables, "a", true);
        assertTrue(l.eval());
    }
    
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
}
