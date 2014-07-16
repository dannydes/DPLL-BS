package com.daniel.dpll;

import com.daniel.dpll.algo.DPLL;
import com.daniel.dpll.algo.ds.Variable;
import com.daniel.dpll.algo.test.RelationEvalTest;
import com.daniel.dpll.parser.Parser;
import java.util.Scanner;

/**
 * Main class
 * @author Daniel
 */
public class Main {

    /**
     * Invocation for parser and DPLL algorithm
     * @param formula string representing formula in CNF
     */
    private static void run(String formula) {
        try {
            Parser parser = Parser.createInstance(formula);

            DPLL dpll = new DPLL(parser);
            
            boolean sat = dpll.isSatisfiable();
            
            System.out.println(sat ? "SAT\n" : "UNSAT");
            
            if (sat) {
                System.out.println("Example literal values: ");
                for (Variable variable : dpll.getVariables()) {
                    System.out.println(variable);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Sentinel test
     * @param formula string representing formula
     * @return whether sentinel test passes or fails
     */
    private static boolean keepRunning(String formula) {
        final String SENTINEL = "exit";
        
        return !SENTINEL.equals(formula);
    }
    
    /**
     * Invocation for relation evaluation JUnit tests
     */
    private static void test() {
        RelationEvalTest.not();
        RelationEvalTest.or();
        RelationEvalTest.and();
    }

    /**
     * Application launch
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("DPLL BS (Bis-Sens)");
        System.out.println("------------------\n");

        if (args.length != 0) {
            if (args[0].equals("--test")) {
                test();
            } else {
                run(args[0]);
            }
        }
        try (Scanner input = new Scanner(System.in)) {
            input.useDelimiter("\n");
            String formula = "";

            while (keepRunning(formula)) {
                System.out.print(">> ");
                formula = input.nextLine();

                if (keepRunning(formula)) {
                    run(formula);
                }
            }
        }
    }

}
