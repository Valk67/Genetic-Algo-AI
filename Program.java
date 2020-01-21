/**
 * Artificial Intelligence, CSCI 580
 * Spring 2019
 *
 * NO NEED TO EDIT (except while debugging?)
 *
 * @author Patrick Donnelly
 * @version 0.2
 */
package geneticcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 * This class represent a Program in the C language. It is represented as a list
 * of Statements, which are each a list of Strings.
 *
 */
public class Program implements Comparable<Program> {

    /**
     * Statements are stored in a list
     */
    private ArrayList<Statement> statements;
    /**
     * Each program tracks its fitness score, initially 0
     */
    private double score = 0;
    /**
     * Each program tracks its last error output from compilation
     */
    private String stdError = "not compiled yet";

    /**
     * Track a probability to select Mutation
     */
    private double probabilityMutation = 1.0;
    /**
     * Track a probability to select Crossover
     */
    private double probabilityCrossover = 1.0;

    /**
     * Construct a new Program with the given statements
     *
     * @param statements the list of statements
     */
    public Program(ArrayList<Statement> statements) {
        this.statements = deepCopy(statements);
    }

    /**
     * Copy constructor for to copy a Program
     *
     * @param program the Program to copy
     */
    public Program(Program program) {
        statements = new ArrayList<>();
        for (Statement s : program.getStatements()) {
            statements.add(new Statement(s));
        }
        score = program.getScore();
        stdError = program.getStdError();
    }

    /**
     * Makes a deep copy of the Program and its Statements
     *
     * @param program the Program to copy
     * @return a new Program containing the same information as the original
     */
    static Program deepCopy(Program program) {
        return new Program(program);
    }

    /**
     * Makes a deep copy of a list of Statements
     *
     * @param statements the list of Statements to deep copy
     * @return a list containing copies of the original Statements
     */
    static ArrayList<Statement> deepCopy(ArrayList<Statement> statements) {
        ArrayList<Statement> list = new ArrayList<>();
        for (Statement s : statements) {
            list.add(new Statement(s));
        }
        return list;
    }

    /**
     * Get the probability of mutation
     *
     * @return a probability
     */
    public double getProbabilityMutation() {
        return probabilityMutation;
    }

    /**
     * Set the probability of mutation
     *
     * @param probabilityMutation the new probability
     */
    public void setProbabilityMutation(double probabilityMutation) {
        this.probabilityMutation = Math.min(Math.max(probabilityMutation, 0.0), 1.0);
    }
    
    /**
     * Set a random probability of mutation
     *
     */
    public void setRandomProbabilityMutation() {
        this.probabilityMutation = (new Random()).nextDouble();
    }    

    /**
     * Get the probability of crossover
     *
     * @return a probability
     */
    public double getProbabilityCrossover() {
        return probabilityCrossover;
    }

    /**
     * Set the probability of crossover
     *
     * @param probabilityCrossover the new probability
     */
    public void setProbabilityCrossover(double probabilityCrossover) {
        this.probabilityCrossover = Math.min(Math.max(probabilityCrossover, 0.0), 1.0);
    }

    /**
     * Set a random probability of crossover
     *
     */
    public void setRandomProbabilityCrossover() {
        this.probabilityCrossover = (new Random()).nextDouble();
    }        
    
    /**
     * Coin toss to decide if to mutate, based on getProbabilityMutation()
     *
     * @return true if should mutate
     */
    public boolean mutate() {
        return (new Random()).nextDouble() <= probabilityMutation;
    }

    /**
     * Coin toss to decide if to crossover, based on getProbabilityCrossover()
     *
     * @return true if should crossover
     */
    public boolean crossover() {
        return (new Random()).nextDouble() <= probabilityCrossover;
    }

    /**
     * Add a Statement to the Program
     *
     * @param statement the new Statement to add
     */
    public void addStatement(Statement statement) {
        statements.add(new Statement(statement));
    }

    /**
     * Add a Statement to the Program at a specific index
     *
     * @param index the index where to add the Statement
     * @param statement the new Statement to add
     */
    public void addStatement(int index, Statement statement) {
        if (index >= 0 && index < statements.size()) {
            statements.add(index, new Statement(statement));
        } else {
            statements.add(new Statement(statement));
        }
    }

    /**
     * Appends the list of Statements to the Program
     *
     * @param toAdd the list of Statements to add
     */
    public void addStatements(ArrayList<Statement> toAdd) {
        statements.addAll(deepCopy(toAdd));
    }

    /**
     * Appends the list of Statements to the Program
     *
     * @param index the index where to add the Statements
     * @param toAdd the list of Statements to add
     */
    public void addStatements(int index, ArrayList<Statement> toAdd) {
        if (index >= 0 && index < statements.size()) {
            statements.addAll(index, deepCopy(toAdd));
        } else {
            statements.addAll(deepCopy(toAdd));
        }
    }

    /**
     * Add a random Statement to the Program
     */
    public void addRandomStatement() {
        statements.add(((new Random()).nextInt(statements.size())), Factory.getRandomStatement());
    }

    /**
     * Add a random Statements to the Program
     *
     * @param n the number of random Statements to add
     */
    public void addRandomStatements(int n) {
        for (int i = 0; i < n; i++) {
            addRandomStatement();
        }
    }

    /**
     * Get a specific statement in the Program
     *
     * @param index the index of the Statement
     * @return the Statement if in range, otherwise null
     */
    public Statement getStatement(int index) {
        index = Math.min(index, statements.size() - 1);
        index = Math.max(index, 0);
        return statements.get(index);
    }

    /**
     * Get the list of Statements in the Program
     *
     * @return the list of statements
     */
    public ArrayList<Statement> getStatements() {
        return statements;
    }
    
    /**
     * Get a deep copy of the list of Statements in the Program
     *
     * @return the list of statements
     */
    public ArrayList<Statement> getStatementsCopy() {
        return deepCopy(statements);
    }    

    /**
     * Get a subset of Statements in the Program
     *
     * @param low the lower index, inclusive
     * @param high the upper index, exclusive
     * @return a list of the Statements if in range, otherwise empty list
     */
    public ArrayList<Statement> getStatements(int low, int high) {
        low = Math.min(low, statements.size() - 1);
        low = Math.max(low, 0);
        high = Math.min(high, statements.size());
        high = Math.max(high, 0);
        if (low >= high) {
            return new ArrayList<>();
        }
        return new ArrayList<>(statements.subList(low, high));
    }

    /**
     * Get a random Statement from the Program
     *
     * @return a random Statement
     */
    public Statement getRandomStatement() {
        return statements.get(((new Random()).nextInt(statements.size())));
    }

    /**
     * Get a list of random consecutive Statements from the Program. Does not
     * alter the Program.
     *
     * @return random subset of Statements
     */
    public ArrayList<Statement> getRandomStatements() {
        ArrayList<Statement> subset = new ArrayList<>();
        Random r = new Random();
        int n = r.nextInt(statements.size());
        int index = r.nextInt(statements.size() - n);
        for (int i = index; i < Math.min(n, statements.size()); i++) {
            subset.add(statements.get(i));
        }
        return subset;
    }

    /**
     * Gets a random subset of non-consecutive Statements
     *
     * @param probability the probability at which to select Statement
     * @return list of tokens
     */
    public ArrayList<Statement> getRandomSubset(double probability) {
        ArrayList<Statement> list = new ArrayList<>();
        probability = Math.min(probability, 1);
        probability = Math.max(probability, 0);
        for (Statement s : statements) {
            double rnd = (new Random()).nextDouble();
            if (rnd < probability) {
                list.add(s);
            }
        }
        return list;
    }

    /**
     * Returns a subset of Statements containing the target token
     *
     * @param target the token to match
     * @return list of Statements containing token
     */
    public ArrayList<Statement> getStatementsContainingToken(String target) {
        ArrayList<Statement> list = new ArrayList<>();
        for (Statement s : statements) {
            if (s.contains(target)) {
                list.add(new Statement(s));
            }
        }
        return list;
    }

    /**
     * Returns a subset of Statements containing any one of the target tokens
     *
     * @param targets list of the tokens to match
     * @return list of Statements containing at least one of the target tokens
     */
    public ArrayList<Statement> getStatementsContainingAnyToken(ArrayList<String> targets) {
        ArrayList<Statement> list = new ArrayList<>();
        for (Statement s : statements) {
            for (String target : targets) {
                if (s.contains(target)) {
                    list.add(new Statement(s));
                }
            }
        }
        return list;
    }

    /**
     * Returns a subset of Statements containing all of of the target tokens
     *
     * @param targets list of the token to match
     * @return list of Statements containing all of the target tokens
     */
    public ArrayList<Statement> getStatementsContainingAllTokens(ArrayList<String> targets) {
        ArrayList<Statement> list = new ArrayList<>();
        for (Statement s : statements) {
            boolean add = true;
            for (String target : targets) {
                add &= s.contains(target);
            }
            if (add) {
                list.add(new Statement(s));
            }
        }
        return list;
    }

    /**
     * Get the longest Statement in the Program
     *
     * @return the longest Statement
     */
    public Statement getLongestStatement() {
        int longest = -1;
        Statement longestStatement = null;
        for (Statement s : statements) {
            if (s.length() > longest) {
                longest = s.length();
                longestStatement = s;
            }
        }
        return longestStatement;
    }

    /**
     * Get the shortest Statement in the Program
     *
     * @return the longest Statement
     */
    public Statement getShortestStatement() {
        int shortest = Integer.MAX_VALUE;
        Statement shortestStatement = null;
        for (Statement s : statements) {
            if (s.length() < shortest) {
                shortest = s.length();
                shortestStatement = s;
            }
        }
        return shortestStatement;
    }

    /**
     * Return an random index between 0 and size()-1
     *
     * @return random index in the Statement list
     */
    public int getRandomIndex() {
        return ((new Random()).nextInt(statements.size()) - 1);
    }

    /**
     * Remove a random Statement from the Program
     */
    public void removeRandomStatement() {
        statements.remove(((new Random()).nextInt(statements.size())));
    }

    /**
     * Remove a number of random Statements from the Program
     *
     * @param n the number of Statements to remove
     */
    public void removeRandomStatements(int n) {
        for (int i = 0; i < n; i++) {
            if (!isEmpty()) {
                removeRandomStatement();
            }
        }
    }

    /**
     * Removes the specified Statement from the Program
     *
     * @param toRemove the Statement to remove
     * @return true, if Program contained the Statement
     */
    public boolean remove(Statement toRemove) {
        return statements.remove(toRemove);
    }

    /**
     * Remove the Statement at the given index if in range
     *
     * @param index the index to remove
     */
    public void removeStatement(int index) {
        if (index >= 0 && index < statements.size()) {
            statements.remove(index);
        }
        // if out of range, NOP
    }

    /**
     * Removes all of the Statements in the given list, if in Program
     *
     * @param toRemove list of Statements to remove
     */
    public void removeStatements(ArrayList<Statement> toRemove) {
        for (Statement r : toRemove) {
            if (statements.contains(r)) {
                statements.remove(r);
            }
        }
    }

    /**
     * Remove a subset of specific statements in the Program by indices
     *
     * @param low the lower index, inclusive
     * @param high the upper index, exclusive
     */
    public void removeStatements(int low, int high) {
        low = Math.min(low, statements.size() - 1);
        low = Math.max(low, 0);
        high = Math.min(high, statements.size());
        high = Math.max(high, 0);
        for (int index = low; index < high; index++) {
            statements.remove(index);
        }
    }

    /**
     * Removes the first Statement
     */
    public void removeFirstStatement() {
        statements.remove(0);
    }

    /**
     * Removes the last Statement
     */
    public void removeLastStatement() {
        statements.remove(statements.size() - 1);
    }

    /**
     * Replace a random Statement with a new random Statement
     */
    public void replaceRandomStatement() {
        Statement rndStatement = getRandomStatement();
        int index = (new Random()).nextInt(statements.size());
        statements.set(index, rndStatement);
    }

    /**
     * Replace a random Statement with a new random Statement, a specified
     * number of times
     *
     * @param n the number of times to replace
     */
    public void replaceRandomStatements(int n) {
        for (int i = 0; i < n; i++) {
            replaceRandomStatement();
        }
    }

    /**
     * Replace a specific Statement with a random Statement
     *
     * @param index the index of the Statement to replace
     */
    public void replaceRandomStatement(int index) {
        Statement rndStatement = getRandomStatement();
        statements.set(index, rndStatement);
    }

    /**
     * Replace a specific Statement with a random Statement
     *
     * @param statement the Statement to replace, if present
     */
    public void replaceRandomStatement(Statement statement) {
        Statement rndStatement = getRandomStatement();
        int index = statements.indexOf(statement);
        if (index > 0 && index < statements.size() - 1) {
            statements.set(index, rndStatement);
        }
    }

    /**
     * Replace a specific Statement with a random Statement
     *
     * @param statement1 the Statement to replace, if present
     * @param statement2 the new Statement that replaces statement1
     */
    public void replaceStatement(Statement statement1, Statement statement2) {
        int index = statements.indexOf(statement1);
        if (index > 0 && index < statements.size() - 1) {
            statements.set(index, statement2);
        }
    }

    /**
     * Pick a random Statement and duplicate in in the program
     */
    public void duplicateRandomStatement() {
        int index = (new Random()).nextInt(statements.size());
        if (index < statements.size() - 1) {
            statements.add(index + 1, new Statement(statements.get(index)));
        } else {
            statements.add(new Statement(statements.get(index)));
        }
    }

    /**
     * Swaps two Statements at random, a specified number of times
     *
     * @param n the number of swaps to perform
     */
    public void swapStatements(int n) {
        for (int i = 0; i < n; i++) {
            swapStatements();
        }
    }

    /**
     * Swaps two Statements, at random
     */
    public void swapStatements() {
        int i = (new Random()).nextInt(statements.size());
        int j = (new Random()).nextInt(statements.size());
        Collections.swap(statements, i, j);
    }

    /**
     * Swaps two Statements by index
     *
     * @param first the index of one element
     * @param second the index of a second element
     */
    public void swapStatements(int first, int second) {
        first = Math.min(first, statements.size() - 1);
        first = Math.max(first, 0);
        second = Math.min(second, statements.size() - 1);
        second = Math.max(second, 0);
        Collections.swap(statements, first, second);
    }

    /**
     * Checks if Statement list is empty
     *
     * @return true if Program contains no Statements
     */
    public boolean isEmpty() {
        return statements.isEmpty();
    }

    /**
     * Return true if this Program contains the queried Statement
     *
     * @param target the Statement to search for
     * @return true if found
     */
    public boolean containsStatement(Statement target) {
        for (Statement s : statements) {
            if (s.toString().equals(target.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if any Statement in this Program contains the queried token
     *
     * @param target the token to search for
     * @return true if found
     */
    public boolean containsToken(String target) {
        for (Statement s : statements) {
            if (s.contains(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the index of the first occurrence of the given Statement
     *
     * @param target the token to find
     * @return the index of the first occurrence if found, -1 otherwise
     */
    public int indexOf(Statement target) {
        for (int i = 0; i < statements.size(); i++) {
            if (statements.get(i).toString().equals(target.toString())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets the index of the first occurrence of a Statement containing the
     * target token
     *
     * @param target the token to find
     * @return the index of the first occurrence if found, -1 otherwise
     */
    public int indexOf(String target) {
        for (int i = 0; i < statements.size(); i++) {
            if (statements.get(i).contains(target)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Append the Statements from the other Program to this Program
     *
     * @param other the Program to append to this Program
     */
    public void append(Program other) {
        for (Statement s : other.getStatements()) {
            statements.add(new Statement(s));
        }
    }

    /**
     * Prepend the Statements from the other Program to this Program
     *
     * @param other the Program to prepend on this Program
     */
    public void prepend(Program other) {
        ArrayList<Statement> temp = deepCopy(other.getStatements());
        Collections.reverse(temp);
        for (Statement s : temp) {
            statements.add(0, new Statement(s));
        }
    }

    /**
     * Insert the Statements from the other Program to this Program at the
     * specified index
     *
     * @param index the index where to splice the other Program
     * @param other the Program to splice into this Program
     */
    public void splice(int index, Program other) {
        if (index > statements.size()) {
            statements.addAll(deepCopy(other.getStatements()));
            return;
        }
        index = Math.max(index, 0);
        statements.addAll(index, deepCopy(other.getStatements()));
    }

    /**
     * Reverse the order of the Statements
     */
    public void reverse() {
        Collections.reverse(statements);
    }

    /**
     * Shuffle the order of the Statements
     */
    public void shuffle() {
        Collections.shuffle(statements);
    }

    /**
     * Rotate the order of the Statements by a random number of indexes.
     * Statements at the end of the list with shift to the front. The size of
     * the list will not change.
     */
    public void rotate() {
        Collections.rotate(statements, (new Random()).nextInt(statements.size()));
    }

    /**
     * Sort the list of Statements by length
     */
    public void sort() {
        Collections.sort(statements);
    }

    /**
     * Static method to make a new Program that alternates Statements from two
     * given Programs
     *
     * @param p1 the first Program
     * @param p2 the second Program
     * @return a new Program that contains Statements from both Programs
     */
    public static Program shuffle(Program p1, Program p2) {
        ArrayList<Statement> list = new ArrayList<>();
        int max = Math.min(p1.size(), p2.size());
        for (int k = 0; k < max; k++) {
            list.add(new Statement(p1.getStatement(k)));
            list.add(new Statement(p2.getStatement(k)));
        }
        if (max < p1.size()) {
            list.addAll(deepCopy(p1.getStatements(max, p1.size() - 1)));
        } else if (max < p2.size()) {
            list.addAll(deepCopy(p2.getStatements(max, p2.size() - 1)));
        }
        return new Program(list);
    }

    /**
     * Static method to split a Program into two new Programs
     *
     * @param program the Program to split
     * @return a list containing two programs
     */
    public static ArrayList<Program> split(Program program) {
        ArrayList<Program> programs = new ArrayList<>();
        ArrayList<Statement> list1 = new ArrayList<>();
        ArrayList<Statement> list2 = new ArrayList<>();

        int split = (new Random()).nextInt(program.size());
        for (int i = 0; i < split; i++) {
            list1.add(new Statement(program.getStatement(i)));
        }
        for (int j = split; j < program.size(); j++) {
            list2.add(new Statement(program.getStatement(j)));
        }
        programs.add(new Program(list1));
        programs.add(new Program(list2));
        return programs;
    }

    /**
     * Get every other Statement, counting with even indices
     *
     * @return a list of Statements
     */
    public ArrayList<Statement> getEvenStatements() {
        ArrayList<Statement> list = new ArrayList<>();
        for (int i = 0; i < statements.size(); i += 2) {
            list.add(new Statement(statements.get(i)));
        }
        return list;
    }

    /**
     * Get every other Statement, counting with odd indices
     *
     * @return a list of Statements
     */
    public ArrayList<Statement> getOddStatements() {
        ArrayList<Statement> list = new ArrayList<>();
        for (int i = 1; i < statements.size(); i += 2) {
            list.add(new Statement(statements.get(i)));
        }
        return list;
    }

    /**
     * Count the total number of tokens across all Statements
     *
     * @return length as a count of the words in the Program
     */
    public int length() {
        int len = 0;
        for (Statement s : statements) {
            len += s.length();
        }
        return len;
    }

    /**
     * Get the average length of Statements in this Program
     *
     * @return the average Statement length
     */
    public double averageStatementLength() {
        double sum = 0.0;
        for (Statement s : statements) {
            sum += s.length();
        }
        return sum / statements.size();
    }

    /**
     * Get the length of the longest Statement in the Program
     *
     * @return the length of the longest Statement
     */
    public int longestStatementLength() {
        return getLongestStatement().length();
    }

    /**
     * Get the length of the shortest Statement in the Program
     *
     * @return the length of the shortest Statement
     */
    public int shortestStatementLength() {
        return getShortestStatement().length();
    }

    /**
     * Count the characters across Statements in this Program
     *
     * @return the character count
     */
    public int characterCount() {
        int count = 0;
        for (Statement statement : statements) {
            count += statement.characterCount();
        }
        return count;
    }

    /**
     * Count the number of occurrences of the given token in the Program
     *
     * @param target the token to count
     * @return the count of the occurrence, otherwise 0
     */
    public int count(String target) {
        int count = 0;
        for (int i = 0; i < statements.size(); i++) {
            count += statements.get(i).count(target);
        }
        return count;
    }

    /**
     * Count the number of Statements in this Program
     *
     * @return length as a count of Statements (lines)
     */
    public int size() {
        return statements.size();
    }

    /**
     * Compares this Program to another Program based on size()
     *
     * @param other the other Program
     * @return -1 if less, 0 if equal, 1 if longer
     */
    public int longer(Program other) {
        return new Integer(this.size()).compareTo(other.size());
    }

    /**
     * Get the fitness score of the Program
     *
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * Get the fitness score transformed by hyperbolic tangent
     *
     * @return the score mapped to (-1,1)
     */
    public double tanhScore() {
        return Math.tanh(score);
    }

    /**
     * Get the fitness score transformed by arc tangent
     *
     * @return the score mapped tan^(-1)(score)
     */
    public double arctanScore() {
        return Math.atan(score);
    }

    /**
     * Get the fitness score transformed by the sigmoid function
     *
     * @return the score mapped to (0,1)
     */
    public double logisticScore() {
        return (1. / (1 + Math.pow(Math.E, (-1 * score))));
    }

    /**
     * Get the rectified linear unit activation of the score
     *
     * @return 0, if score less than 0, score otherwise
     */
    public double reluScore() {
        return Math.max(0, score);
    }

    /**
     * Get the fitness score mapped to binary 0 or 1
     *
     * @return 0 if score less than 0, 1 otherwise
     */
    public int binaryScore() {
        return score < 0 ? 0 : 1;
    }

    /**
     * Gets the fitness score as an exponential linear unit
     *
     * @return e^(score-1) if score less than 0, score otherwise
     */
    public double exponentiaScore() {
        return score < 0 ? Math.pow(Math.E, score - 1) : score;
    }

    /**
     * Update the fitness score for this Program
     *
     * @param score the new fitness score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Get the stderr from last compilation
     *
     * @return the stderr message
     */
    public String getStdError() {
        return stdError;
    }

    /**
     * Update the stderr message from last compilation
     *
     * @param stdError the new stderr message
     */
    public void setStdError(String stdError) {
        this.stdError = stdError;
    }

    /**
     * Count the words in the standard error message
     *
     * @return count of words in error
     */
    public int lengthStdErr() {
        return stdError.split("\\s").length;
    }

    /**
     * Create a String representing the program as a main() method
     *
     * @return String representation of the program
     */
    @Override
    public String toString() {
        String str = "int main(){\n";
        for (Statement s : statements) {
            str += "   " + s + "\n";
        }
        str += "}";
        return str;
    }

    /**
     * Produces a hashCode representation of this Program
     *
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.statements);
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.score) ^ (Double.doubleToLongBits(this.score) >>> 32));
        hash = 73 * hash + Objects.hashCode(this.stdError);
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.probabilityMutation) ^ (Double.doubleToLongBits(this.probabilityMutation) >>> 32));
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.probabilityCrossover) ^ (Double.doubleToLongBits(this.probabilityCrossover) >>> 32));
        return hash;
    }

    /**
     * Compares the given Program to this Program. Returns true if they are the
     * same object, or if they are different but contain the same Statements
     *
     * @param obj the other Program to compare
     * @return true, if equal by memory or toString()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        String str1 = this.toString();
        String str2 = ((Program) obj).toString();

        return str1.equals(str2);
    }

    /**
     * Compares this Program to another Program based on fitness score
     *
     * @param other the other Program
     * @return -1 if less, 0 if equal, 1 if longer
     */
    @Override
    public int compareTo(Program other) {
        // Program objects will be compared by fitness score
        // This makes lists of Programs sortable by score
        return new Double(score).compareTo(other.getScore());
    }
}
