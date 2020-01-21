/**
 * Artificial Intelligence, CSCI 580
 * Spring 2019
 *
 * NO NEED TO EDIT (except while debugging?)
 *
 * @author Patrick Donnelly
 */
package geneticcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class compiles the Program and calculates a fiteness score.
 *
 */
public class Fitness {

    private static final double ERROR_WORD_PENALTY = -1.0;
    private static final double UNIQUE_WORD_BONUS = 2.0;
    private static final double LENGTH_WORD_BONUS = 1.0;
    private static final double LENGTH_CHAR_BONUS = 0.1;

    // Dummy constructor to prevent object instances
    private Fitness() {
    }

    /**
     * Update the fitness score for the entire population
     *
     * @param population the population to score
     */
    public static synchronized void fitness(ArrayList<Program> population) {
        List<Program> lst = Collections.synchronizedList(population);        
        lst.stream().forEach(p -> Fitness.fitness(p));
        // Sort the population by score
        Collections.sort(population);
    }

    /**
     * Calculate the fitness score.
     *
     * Rewards length and diversity of tokens. Penalizes number of words in the
     * error output.
     *
     * This method updates the Program's fitness score, and also returns it.
     *
     * @param program the program to compile
     * @return the score
     */
    private static synchronized double fitness(Program program) {

        // initial score
        double score = 0;

        // One point for each token in solution (favors long programs)
        score += LENGTH_WORD_BONUS * program.length();

        // One-tenth point for each character in solution (favors long programs)
        score += LENGTH_CHAR_BONUS * program.toString().replaceAll("\\s", "").replaceAll(";", "").length();

        // Two points for each unique token (favors diversity)
        score += UNIQUE_WORD_BONUS * (new HashSet<>(new ArrayList<>(Arrays.asList(program.toString().split("\\s"))))).size();

        // Now compile and count the errors
        String progstr = "" + program;   // Call toString and convert to text
        try {
            // Make a temporary file called "main.cpp"
            BufferedWriter writer = new BufferedWriter(new FileWriter("./main.cpp"));
            writer.write(progstr);
            writer.close();

            // Use system command to compile using gcc
            //Process p = Runtime.getRuntime().exec("gcc -Wall -pedantic ./main.cpp");
            Process p = Runtime.getRuntime().exec("gcc -pipe ./main.cpp");

            String s = null;
            //BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // Read any errors from gcc and concat them
            String stderr = "";
            while ((s = stdError.readLine()) != null) {
                if (!s.trim().equals(" ")) {
                    stderr += s;
                }
            }
            stderr = stderr.trim();

            // Save the error output
            //System.out.println("Error: "+stderr);
            program.setStdError(stderr);

            // Count the number of words (split by whitespace) in compiler error output
            int err = stderr.split("\\s").length - 1;

            // Subtract the number of error-words from the total score
            score += ERROR_WORD_PENALTY * err;
        } catch (IOException ex) {
            Logger.getLogger(Fitness.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Update the program's score
        program.setScore(score);
        return score;
    }
}
