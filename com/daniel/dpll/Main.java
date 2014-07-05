package com.daniel.dpll;

import com.daniel.dpll.algo.DPLL;
import com.daniel.dpll.parser.Parser;
import java.util.Scanner;

public class Main {

    private static void run(String formula) {
        try {
            Parser parser = Parser.createInstance(formula);

            DPLL dpll = new DPLL(parser);
            System.out.println(dpll.isSatisfiable());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("DPLL BS (Bis-Sens)");
        System.out.println("------------------\n");

        if (args.length != 0) {
            run(args[0]);
        }
        try (Scanner input = new Scanner(System.in)) {
            input.useDelimiter("\n");
            String formula;

            final String SENTINEL = "exit";

            do {
                System.out.print(">> ");
                formula = input.nextLine();

                run(formula);
            } while (!SENTINEL.equals(formula));
        }
    }

}
