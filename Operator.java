/**
 * Artificial Intelligence, CSCI 580
 * Spring 2019
 *
 * EDIT this file heavily
 *
 * @author <Bradley Sundsbarm.  bsundsbarm. >
 */
package geneticcode;

import java.util.ArrayList;
import java.util.Random;
//added imports by me
import java.lang.Math;
import static java.lang.Math.toIntExact;

/**
 * Static collection of Generic Operators and their helper functions.
 */
public class Operator {
    // Random seed
    private static final Random r = new Random();

    /**
     * You can access any public static function, such as Main.gMAX *
     * Factory.getRandomKeyword() etc.
     */

    // Dummy constructor to prevent object instances
    private Operator() {
    }

    /**
     * Pick two Programs at random and select the one with the higher score
     * @param population the population to select from
     * @return the superior Program
     */
    public static Program tournamentSelection(ArrayList<Program> population){
        int i = r.nextInt(population.size());
        int j = r.nextInt(population.size());
        return population.get(i).getScore() > population.get(j).getScore() ? population.get(i) : population.get(j);
    }


    /**
     * Select the next generation of Programs
     *
     * @param prevGeneration a population from which to select
     * @param generation the generation number
     * @return the selected population
     */
    public static ArrayList<Program> selection(ArrayList<Program> prevGeneration, int generation) {
        // Copy from one generation to the next
        ArrayList<Program> nextGeneration = prevGeneration;
        /*my approach was a bit random, first selection only occurs if the generation is divisible,
          by 2 or 5.  Next I scan for the largest score and if another persons score is less than that and
          divisible by 3 then it is removed and another child is added to the population.  Note
          that I semi intertiwined crossover in a way here since I created children and added them.
        */
        double largestFitness = 0;
        Program child;
        Random randInt = new Random();
        if (generation % 2 == 0 || generation % 5 == 0) {
          //was going to originally recalculate fitness but then saw the time increase and
          //figured with a bigger population it would exponentially increase my run time.
          //so I through out that method and just stuck with the finess calls from main to settle
          //the fitness scores.
          //Fitness.fitness(nextGeneration);
          for (int i = randInt.nextInt(1); i < nextGeneration.size(); i = i + 2) {
            //record the largest score to use later on.
            if (nextGeneration.get(i).getScore() > largestFitness) {
              largestFitness = nextGeneration.get(i).getScore();
            }
            if (nextGeneration.get(i).getScore() < -5) {
              child = tournamentSelection(nextGeneration);
              nextGeneration.remove(i);
              nextGeneration.add(child);
            }
            if ((nextGeneration.get(i).getScore() < largestFitness) && (i % 3 == 0)) {
              nextGeneration.remove(i);
              child = tournamentSelection(nextGeneration);
              nextGeneration.add(child);
            }
            //extra code that was not used

            // double a = 0; double b = 0;
            // a = nextGeneration.get(randInt.nextInt(nextGeneration.size())).getScore();
            // int j = randInt.nextInt(5);
            // int x = 2;
            // while(x > 0) {
            //   if (nextGeneration.get(j).getScore() == a) {
            //     nextGeneration.remove(j);
            //     child = tournamentSelection(nextGeneration);
            //     nextGeneration.add(child);
            //   }
            //   x--;
            //   j++;
            // }
          }
       }//end of the if

       // for (int i = 0; i < 10; i++) {
       //   System.out.println(nextGeneration.get(i).getScore());
       // }

        return nextGeneration;
    }

    /**
     * Perform crossover across an initial population
     *
     * @param population the initial population to draw from
     * @param generation the generation number
     * @return a new population of children
     */
    public static ArrayList<Program> crossover(ArrayList<Program> population, int generation) {
        ArrayList<Program> children = new ArrayList<>();

        /*
        my crossover I chose a random approach that for every iteration of the for looping
        3 parents are considered.  First 2 are by random then once I have those I compare
        them and send the stronger to cossover with whoever is it iteration i.
        */
        Random randInt2 = new Random();

        for (int i = 0; i < population.size(); i++) {

          int a = randInt2.nextInt(population.size());
          Program Parent1 = tournamentSelection(population);
          Program Parent2 = tournamentSelection(population);
          if (Parent1.getScore() < Parent2.getScore()) {
            Parent1 = Parent2;
          }
          if (Parent1.getScore() < population.get(i).getScore()) {
            Parent1 = population.get(i);
          }
          Program child = crossover(Parent1, population.get(a));
          //population.remove(a);
          children.add(child);

        }
        return children;
    }

    /**
     * Perform the crossover between two parents
     *
     * @param program1 the first parent
     * @param program2 the second parent
     * @return a new Program recombined from the two parents
     */
    public static Program crossover(Program program1, Program program2) {
        /**
         * This does not perform crossover. Write a better crossover operator.
         */

        // Naively, the child is just the first parent.  Do better.
        Program childProgram = program1;

        return childProgram;
    }

    /**
     * Mutate the population (or just a subset)
     *
     * @param population the initial population to draw from
     * @param generation the generation number
     * @return a new population of mutants, possibly zombies
     */
    public static ArrayList<Program> mutate(ArrayList<Program> population, int generation) {
        ArrayList<Program> mutants = new ArrayList<>();

        /*For mutate I wanted to keep it rolling with my randomness approach and so far
        the results look good my completed times went down without effecting results.
        So if the generation is odd I set the probablity to 2 and then fectch the probability
        if it returns greater than one it mutates. otherwise it just keeps the same program
        in the population.
        */
         for (int i = 0; i < population.size(); i++) {
           double d = 0;
           if (generation % 2 != 0) {
             d = 2;
           }
           Program mutant = population.get(i);
           population.get(i).setProbabilityMutation(d);
           if (population.get(i).getProbabilityMutation() > 1) {
             mutant = mutate(mutant);
           }
           mutants.add(mutant);
         }
         // System.out.println(population.get(1).getProbabilityMutation());
         // population.get(1).setProbabilityMutation();
         // System.out.println(population.get(1).getProbabilityMutation());
         // System.out.println(" ");


         // for (int i = 0; i < 10; i++) {
         //   System.out.println(population.get(i).getScore());
         // }


        return mutants;
    }

    /**
     * Mutate an individual program
     *
     * @param program the program to mutate
     * @return the mutated Program
     */
    public static Program mutate(Program program) {

        /**
         * This mutation doesn't mutate. Write a better mutation operator.
         */
        return program;
    }

    /**
     * ******************************
     * NEED MORE METHODS?
     *
     * Add your static methods below.
     *
     ******************************
     */

}
