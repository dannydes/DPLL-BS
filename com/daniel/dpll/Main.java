package com.daniel.dpll;

import com.daniel.dpll.algo.DPLL;
import com.daniel.dpll.algo.ds.Variable;
import com.daniel.dpll.parser.Parser;
import java.util.Scanner;

public class Main {

    private static void run(String formula) {
        try {
            Parser parser = Parser.createInstance(formula);

            DPLL dpll = new DPLL(parser);
            
            boolean sat = dpll.isSatisfiable();
            
            System.out.println(sat ? "SAT" : "UNSAT");
            
            if (sat) {
                for (Variable variable : dpll.getVariables()) {
                    System.out.println(variable);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static boolean keepRunning(String formula) {
        final String SENTINEL = "exit";
        
        return !SENTINEL.equals(formula);
    }

    public static void main(String[] args) {
        System.out.println("DPLL BS (Bis-Sens)");
        System.out.println("------------------\n");

        if (args.length != 0) {
            run(args[0]);
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
