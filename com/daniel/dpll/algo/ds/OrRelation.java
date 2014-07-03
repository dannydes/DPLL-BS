package com.daniel.dpll.algo.ds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrRelation extends Relation {
	private final HashSet<NotRelation> notRelations = new HashSet();
        private Variable lastUnassigned;
	
        public final HashSet<NotRelation> getNotRelations() {
            return notRelations;
        }
        
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
        
        public final Variable getLastUnassigned() {
            return lastUnassigned;
        }
        
    /**
     *
     * @return
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
     *
     * @param relation
     * @return
     * @throws Exception
     */
    public static OrRelation parse(String relation, ArrayList<Variable> variables) throws Exception {
                OrRelation orRel = new OrRelation();
            
		 Pattern firstOrPattern = Pattern.compile("!?\\s*\\w");
                 Matcher matcher = firstOrPattern.matcher(relation);
		 if (matcher.find()) {
                     orRel.notRelations.add(NotRelation.parse(matcher.group(), variables));
                 }
		 
		 Pattern restOrPattern = Pattern.compile(",\\s*!?\\s*\\w");
		 matcher = restOrPattern.matcher(relation);
                 while (matcher.find()) {
                     orRel.notRelations.add(NotRelation.parse(matcher.group(), variables));
                 }
		 	 
		 return orRel;
	}
}
