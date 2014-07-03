package com.daniel.dpll.algo.ds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndRelation extends Relation implements Cloneable {
	private final HashSet<OrRelation> orRelations = new HashSet();
	
    /**
     *
     * @return
     */
    @Override
	public boolean eval() {
		for (OrRelation rel : orRelations) {
			if (!rel.eval()) {
				return false;
			}
		}
		return true;
	}
	
	public static AndRelation parse(String formula, ArrayList<Variable> variables) throws Exception {
		Pattern andPattern = Pattern.compile("\\(\\s*[^\\)]+\\s*\\)");
		Matcher matcher = andPattern.matcher(formula);
		AndRelation andRel = new AndRelation();
                
		while (matcher.find()) {
			andRel.orRelations.add(OrRelation.parse(matcher.group(), variables));
		}
                
		return andRel;
	}
        
        public HashSet<OrRelation> getOrRelations() {
            return orRelations;
        }
}
