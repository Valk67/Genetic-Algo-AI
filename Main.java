
package geneticcode;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The main() function runs the genetic algorithm.
 *
 */
public class Main {

    // These are the defaults
    // You can access from anywhere statically, e.g., Main.gMAX
    /**
     * k is number of tokens
     */
    public static int kMAX = 1;
    /**
     * g is number of generations
     */
    public static int gMAX = 10;
    /**
     * n is size of population
     */
    public static int nMAX = 10;

    // Set a cutoff time in which progam terminates
    private static int TIME_CUTOFF_IN_SECONDS = 3600;

    // Dummy constructor to prevent object instances
    private Main() {
    }

    
    private static void geneticAlgorithm(long startTime) {
        ArrayList<Program> population = new ArrayList<>();
        // Create the initial population
        for (int n = 0; n < nMAX; n++) {
            population.add(Factory.makeRandomProgram(kMAX));
        }

        // Score the initial population
        Fitness.fitness(population);

        // For each generation...
        for (int g = 1; g <= gMAX; g++) {
            System.out.println("Generation " + g);

            // Round 1: Selection. Fight for survival.
            population = Operator.selection(population, g);
            // Post-condition: population.size() < nMax  

            // Round 2: Recombination. Pass on your knowledge.
            population = Operator.crossover(population, g);
            // Post-condition: population.size() == nMax

            // Round 3: Mutation. Strive to be better. 
            population = Operator.mutate(population, g);
            // Post-condition: you have mutants in your population

            // Terminate if population is too small or too big
            if (population.size() < nMAX) {
                System.err.println("Population is too small!  Breed more");
                System.exit(1);
            } else if (population.size() < nMAX) {
                System.err.println("Population is too big!  Kill more.");
                System.exit(2);
            }

            // Score the population
            Fitness.fitness(population);

            // Check time elapsed against max time allowed
            long timeElapsed = System.currentTimeMillis() - startTime;
            if (timeElapsed / 1000 > TIME_CUTOFF_IN_SECONDS) {
                System.err.println("You exceeded the cutoff time threshold of " + TIME_CUTOFF_IN_SECONDS + " seconds.");
                System.exit(3);
            }
        }

        /**
         * The Debriefing (Results)
         */
        Program best = population.get(population.size() - 1);
        double bestScore = best.getScore();
        String bestError = best.getStdError();

        // You are "scored" based on these results
        System.out.println("");
        System.out.println("Score = " + String.format("%.2f", bestScore));
        System.out.println("Errors:\n " + bestError);
        System.out.println("Program:\n" + best);

    }

    public static void main(String[] args) {
        
        if (args.length == 3) {
            try {
                kMAX = Integer.parseInt(args[0]);
                gMAX = Integer.parseInt(args[1]);
                nMAX = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Arguments must be integers. We're done here. Good-day!");
                System.exit(1);
            }
        }

        // Run the Gentic Algorithm
        long startTime = System.currentTimeMillis();
        geneticAlgorithm(startTime);
        long timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("Finished in " + 1. * timeElapsed / 1000 + " seconds");
    }

}
