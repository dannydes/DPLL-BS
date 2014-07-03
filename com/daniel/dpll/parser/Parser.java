package com.daniel.dpll.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.daniel.dpll.algo.ds.AndRelation;
import com.daniel.dpll.algo.ds.Variable;

public class Parser {
	private static Parser instance = null;
	
	private ArrayList<Variable> variables;
	private AndRelation andRelation;
	
	private Parser(String formula) throws Exception {
		modInstance(formula);
	}
	
	private void modInstance(String formula) throws Exception {
		variables = new ArrayList<Variable>();
		andRelation = null;
		
		setVars(formula);
		setAndRel(formula);
	}
	
	public static Parser createInstance(String formula) throws Exception {
		if (instance == null) {
			instance = new Parser(formula);
		} else {
			instance.modInstance(formula);
		}
		
		return instance;
	}
        
        public static Parser getInstance() {
            return instance;
        }
	
	private void setVars(String formula) {
		Pattern varPattern = Pattern.compile("(\\w)");
		Matcher matcher = varPattern.matcher(formula);
		
		while (matcher.find()) {
                        String match = matcher.group();
                        if (!varExists(match.charAt(0))) {
				variables.add(new Variable(match));
			}
		}
	}
	
	private boolean varExists(char var) {
		for (Variable variable : variables) {
			if (variable.getVariable() == var) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Variable> getVars() {
		return variables;
	}
	
	private void setAndRel(String formula) throws Exception {
		andRelation = AndRelation.parse(formula, variables);
	}
	
	public AndRelation getAndRel() {
		return andRelation;
	}
}
