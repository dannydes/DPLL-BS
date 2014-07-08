package com.daniel.dpll.algo.ds;

import com.daniel.dpll.parser.exceptions.AndRelationParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndRelation extends Relation {

    private final HashSet<OrRelation> orRelations = new HashSet();

    /**
     *
     * @return
     */
    @Override
    public boolean eval() {
        for (OrRelation rel : orRelations) {
            if (rel.getNotRelations().isEmpty()) {
                return true;
            }
            
            if (!rel.eval()) {
                return false;
            }
        }
        return true;
    }

    public static AndRelation parse(String formula, ArrayList<Variable> variables) throws Exception {
        Pattern andPattern = Pattern.compile("\\s*\\(\\s*[^\\)]+\\s*\\)\\s*");
        Matcher matcher = andPattern.matcher(formula);
        AndRelation andRel = new AndRelation();
        
        int pos = 0;

        while (matcher.find()) {
            if (matcher.start() != pos) {
                throw new AndRelationParseException();
            }
            andRel.orRelations.add(OrRelation.parse(matcher.group(), variables));
            
            pos = matcher.end();
        }
        
        if (pos < formula.length()) {
            throw new AndRelationParseException();
        }

        return andRel;
    }

    public HashSet<OrRelation> getOrRelations() {
        return orRelations;
    }
}
