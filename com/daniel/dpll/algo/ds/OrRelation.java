package com.daniel.dpll.algo.ds;

import com.daniel.dpll.parser.exceptions.OrRelationParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing a clause
 * @author Daniel
 */
public class OrRelation extends Relation {

    /**
     * Set of literals
     */
    private final HashSet<NotRelation> notRelations = new HashSet();
    
    /**
     * Last detected unassigned variable
     */
    private Variable lastUnassigned;

    /**
     * Getter for notRelations
     * @return set of literals
     */
    public final HashSet<NotRelation> getNotRelations() {
        return notRelations;
    }

    /**
     * Checks whether the clause contains only one unassigned literal
     * @return whether clause is unit
     */
    public final boolean isUnit() {
        int unassigned = 0;
        for (NotRelation literal : notRelations) {
            if (literal.getVariable().isUnassigned()) {
                unassigned++;
                lastUnassigned = literal.getVariable();
            }
        }
        return unassigned == 1;
    }

    /**
     * Getter for lastUnassigned
     * @return 
     */
    public final Variable getLastUnassigned() {
        return lastUnassigned;
    }

    /**
     * Evaluates clause based on the values assigned to its literals
     * @return clause value
     */
    @Override
    public boolean eval() {
        for (NotRelation rel : notRelations) {
            if (rel.eval()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses a clause substring within the formula string
     * @param relation clause substring
     * @param variables list of variables
     * @return OrRelation instance representing parsed substring
     * @throws Exception
     */
    public static OrRelation parse(String relation, ArrayList<Variable> variables) throws Exception {
        OrRelation orRel = new OrRelation();
        
        Pattern openBracketPattern = Pattern.compile("\\(");
        Matcher matcher = openBracketPattern.matcher(relation);
        matcher.find();
        int pos = matcher.end();

        Pattern firstOrPattern = Pattern.compile("!?\\s*\\w");
        matcher = firstOrPattern.matcher(relation);
        if (matcher.find()) {
            if (pos != matcher.start()) {
                throw new OrRelationParseException();
            }
            orRel.notRelations.add(NotRelation.parse(matcher.group(), variables));
            
            pos = matcher.end();
        }

        Pattern restOrPattern = Pattern.compile("\\s*,\\s*!?\\s*\\w\\s*");
        matcher = restOrPattern.matcher(relation);
        while (matcher.find()) {
            if (pos != matcher.start()) {
                throw new OrRelationParseException();
            }
            orRel.notRelations.add(NotRelation.parse(matcher.group(), variables));
            
            pos = matcher.end();
        }
        
        Pattern closeBracketPattern = Pattern.compile("\\)");
        matcher = closeBracketPattern.matcher(relation);
        matcher.find();
        
        if (pos < matcher.start()) {
            throw new OrRelationParseException();
        }

        return orRel;
    }
}
