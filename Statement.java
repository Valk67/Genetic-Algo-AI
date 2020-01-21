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
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

/**
 * This class represent a statement in the C language. It is represented as a
 * list of strings.
 *
 */
public class Statement implements Comparable<Statement> {

    /**
     * Each statement is a list of strings
     */
    private ArrayList<String> tokens;

    /**
     * Constructor to make a new Statement
     *
     * @param tokens list of Strings in Statement
     */
    public Statement(ArrayList<String> tokens) {
        this.tokens = deepCopy(tokens);
    }

    /**
     * Constructor to make a copy of a Statement
     *
     * @param statement the Statement to copy
     */
    public Statement(Statement statement) {
        // Strings are immutable, so this is same as deep copy
        tokens = new ArrayList<>(statement.getTokens());
    }

    /**
     * Makes a deep copy of the Statement and its tokens
     *
     * @param statement the Statement to copy
     * @return a new Statement containing the same information as the original
     */
    static Statement deepCopy(Statement statement) {
        return new Statement(statement);
    }

    /**
     * Makes a deep copy of a list of tokens
     *
     * @param tokens the list of tokens to deep copy
     * @return a list containing copies of the original tokens
     */
    static ArrayList<String> deepCopy(ArrayList<String> tokens) {
        ArrayList<String> list = new ArrayList<>();
        for (String t : tokens) {
            list.add(t);
        }
        return list;
    }

    /**
     * Add a new token to the Statement
     *
     * @param token the token to add
     */
    public void addToken(String token) {
        tokens.add(token);
    }

    /**
     * Add a new token to the Statement at a specific index
     *
     * @param index the index where to add the token
     * @param token the token to add
     */
    public void addToken(int index, String token) {
        index = Math.min(index, tokens.size());
        index = Math.max(index, 0);
        tokens.add(index, token);
    }

    /**
     * Add the list of tokens to the end of the Statement
     *
     * @param tokens the tokens to add
     */
    public void addTokens(ArrayList<String> tokens) {
        this.tokens.addAll(tokens);
    }

    /**
     * Add the list of tokens to the end of the Statement
     *
     * @param index the location to add the tokens
     * @param tokens the tokens to add
     */
    public void addTokens(int index, ArrayList<String> tokens) {
        if (index >= this.tokens.size()) {
            // add to end
            this.tokens.addAll(tokens);

        }
        index = Math.max(index, 0);
        this.tokens.addAll(index, tokens);
    }

    /**
     * Add a random token somewhere in the Statement
     */
    public void addRandomToken() {
        tokens.add(((new Random()).nextInt(tokens.size())), Factory.getRandomToken());
    }

    /**
     * Add a random tokens somewhere in the Statement, a specified number of
     * times
     *
     * @param n the number of random tokens to add
     */
    public void addRandomTokens(int n) {
        for (int i = 0; i < n; i++) {
            addRandomToken();
        }
    }

    /**
     * Get a specific token in the Program
     *
     * @param index the index of the token
     * @return the token if in range
     */
    public String getToken(int index) {
        index = Math.min(index, tokens.size() - 1);
        index = Math.max(index, 0);
        return tokens.get(index);
    }

    /**
     * Get a subset of specific tokens in Statement
     *
     * @param low the lower index, inclusive
     * @param high the upper index, exclusive
     * @return a list of the tokens, potentially empty
     */
    public ArrayList<String> getTokens(int low, int high) {
        low = Math.min(low, tokens.size() - 1);
        low = Math.max(low, 0);
        high = Math.min(high, tokens.size());
        high = Math.max(high, 0);
        if (low >= high) {
            return new ArrayList<>();
        }
        return new ArrayList<>(tokens.subList(low, high));
    }

    /**
     * Gets a random subset of consecutive tokens
     *
     * @return list of tokens
     */
    public ArrayList<String> getRandomTokens() {
        ArrayList<String> list = new ArrayList<>();
        int i = (new Random()).nextInt(tokens.size());
        int j = (new Random()).nextInt(tokens.size());
        int min = Math.min(i, j);
        int max = Math.max(i, j);
        for (int k = min; k < max; k++) {
            list.add(tokens.get(k));
        }
        return list;
    }

    /**
     * Get a random token from this Statement
     *
     * @return a random token
     */
    public String getRandomToken() {
        return tokens.get((new Random()).nextInt(tokens.size()));
    }

    /**
     * Get a list of random tokens from this Statement
     *
     * @param n the number of random tokens
     * @return a list of random tokens in this Statement
     */
    public ArrayList<String> getRandomToken(int n) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(getRandomToken());
        }
        return list;
    }

    /**
     * Gets a random subset of non-consecutive tokens.
     *
     * @param probability the probability at which to select tokens
     * @return list of tokens
     */
    public ArrayList<String> getRandomSubset(double probability) {
        // Tokens stay in original order, however, some are not selected
        ArrayList<String> list = new ArrayList<>();
        probability = Math.min(probability, 1);
        probability = Math.max(probability, 0);
        for (String t : tokens) {
            double rnd = (new Random()).nextDouble();
            if (rnd < probability) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * Get the list of tokens
     *
     * @return the list of tokens
     */
    public ArrayList<String> getTokens() {
        return tokens;
    }

    /**
     * Get a deep copy of the list of tokens
     *
     * @return the list of tokens
     */
    public ArrayList<String> getTokensCopy() {
        return deepCopy(tokens);
    }

    /**
     * Return an random index between 0 and size()-1
     *
     * @return random index in the token list
     */
    public int getRandomIndex() {
        return ((new Random()).nextInt(tokens.size()) - 1);
    }

    /**
     * Remove a random token from the Statement
     */
    public void removeRandomToken() {
        tokens.remove(((new Random()).nextInt(tokens.size())));
    }

    /**
     * Remove a specified number of random tokens from the Statement
     *
     * @param n the number of random tokens to remove
     */
    public void removeRandomTokens(int n) {
        for (int i = 0; i < n; i++) {
            if (!isEmpty()) {
                removeRandomToken();
            }
        }
    }

    /**
     * Remove the token at the given index
     *
     * @param index the index to remove
     */
    public void removeToken(int index) {
        if (index >= 0 && index < length()) {
            tokens.remove(index);
        }
        // if out of range, NOP
    }

    /**
     * Remove a subset of specific tokens in Statement
     *
     * @param low the lower index, inclusive
     * @param high the upper index, exclusive
     * @return a list of the tokens if in range, otherwise null
     */
    public boolean removeTokens(int low, int high) {
        if (low < high) {
            return false;
        }
        low = Math.min(low, tokens.size() - 1);
        low = Math.max(low, 0);
        high = Math.min(high, tokens.size());
        high = Math.max(high, 0);
        for (int index = low; index < high; index++) {
            tokens.remove(index);
        }
        return true;
    }

    /**
     * Remove each token of a list of tokens
     *
     * @param tokens the list of tokens to remove
     */
    public void removeTokens(ArrayList<String> tokens) {
        for (String token : tokens) {
            this.tokens.remove(token);
        }
    }

    /**
     * Replace the first occurrence of one token with another token
     *
     * @param token1 the token to replace
     * @param token2 the new token
     */
    public void replaceOnce(String token1, String token2) {
        for (int i = 0; i < tokens.size(); i++) {
            if (token1.equals(tokens.get(i))) {
                tokens.set(i, token2);
                return;
            }
        }
    }

    /**
     * Replace all occurrences of one token with another token
     *
     * @param token1 the token to replace
     * @param token2 the new token
     */
    public void replaceAll(String token1, String token2) {
        for (int i = 0; i < tokens.size(); i++) {
            if (token1.equals(tokens.get(i))) {
                tokens.set(i, token2);
            }
        }
    }

    /**
     * Update the list of tokens
     *
     * @param tokens the new ArrayList of tokens
     */
    public void setTokens(ArrayList<String> tokens) {
        this.tokens = tokens;
    }

    /**
     * Get the token most frequently occurring in the Statement
     *
     * @return the most frequent token
     */
    public String mostCommonToken() {
        HashMap<String, Integer> counts = new HashMap<>();
        for (String t : tokens) {
            switch (t) {
                case ";":
                case "(":
                case ")":
                case "{":
                case "}":
                case "[":
                case "]":
                    // don't count these symbols
                    continue;
                default:
                    if (counts.containsKey(t)) {
                        counts.put(t, counts.get(t) + 1);
                    } else {
                        counts.put(t, 1);
                    }
            }
        }

        String mostCommonToken = "";
        int mostCommonCount = -1;
        for (String t : tokens) {
            if (counts.get(t) > mostCommonCount) {
                mostCommonToken = t;
                mostCommonCount = counts.get(t);
            }
        }
        return mostCommonToken;
    }

    /**
     * Count the number of tokens (Strings) in the Statements
     *
     * @return the length of the Statement
     */
    public int length() {
        return tokens.size();
    }

    /**
     * Count the characters across tokens in this Statement
     *
     * @return the character count
     */
    public int characterCount() {
        int count = 0;
        for (String token : tokens) {
            count += token.length();
        }
        return count;
    }

    /**
     * Checks if the token is present in the Statement
     *
     * @param target the token to find
     * @return true if found
     */
    public boolean contains(String target) {
        return tokens.stream().anyMatch((token) -> (token.equals(target.trim())));
    }

    /**
     * Gets the index of the first occurrence of the given token
     *
     * @param target the token to find
     * @return the index of the first occurrence if found, -1 otherwise
     */
    public int indexOf(String target) {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals(target.trim())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Count the number of occurrences of the given token
     *
     * @param target the token to count
     * @return the count of the occurrence, otherwise 0
     */
    public int count(String target) {
        int count = 0;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals(target.trim())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Append the tokens from the other Statement to this Statement
     *
     * @param other the Statement to append after this Statement
     */
    public void append(Statement other) {
        for (String t : other.getTokens()) {
            tokens.add(t);
        }
    }

    /**
     * Prepend the tokens from the other Statement to this Statement
     *
     * @param other the Statement to prepend on this Statement
     */
    public void prepend(Statement other) {
        ArrayList<String> temp = deepCopy(other.getTokens());
        Collections.reverse(temp);
        for (String t : temp) {
            tokens.add(0, t);
        }
    }

    /**
     * Insert the tokens from the other Statement to this Statement at the
     * specified index
     *
     * @param index the index where to splice the other Statement
     * @param other the Statement to splice into on this Statement
     */
    public void splice(int index, Statement other) {
        if (index > tokens.size()) {
            tokens.addAll(deepCopy(other.getTokens()));
            return;
        }
        index = Math.max(index, 0);
        tokens.addAll(index, deepCopy(other.getTokens()));
    }

    /**
     * Reverse the order of the tokens; probably not a useful operation.
     */
    public void reverse() {
        Collections.reverse(tokens);
    }

    /**
     * Shuffle the order of the tokens; probably not a useful operation.
     */
    public void shuffle() {
        Collections.shuffle(tokens);
    }

    /**
     * Swaps two tokens, at random, a specified number of times
     *
     * @param n the number of swaps to perform
     */
    public void swapTokens(int n) {
        for (int i = 0; i < n; i++) {
            swapTokens();
        }
    }

    /**
     * Swaps two tokens, at random
     */
    public void swapTokens() {
        int i = (new Random()).nextInt(tokens.size());
        int j = (new Random()).nextInt(tokens.size());
        Collections.swap(tokens, i, j);
    }

    /**
     * Swaps two tokens, by index
     *
     * @param first the index of one element
     * @param second the index of a second element
     */
    public void swapTokens(int first, int second) {
        first = Math.min(first, tokens.size() - 1);
        first = Math.max(first, 0);
        second = Math.min(second, tokens.size() - 1);
        second = Math.max(second, 0);
        Collections.swap(tokens, first, second);
    }

    /**
     * Checks if parentheses, brackets, and braces are balanced
     *
     * @return true if balanced, false otherwise
     */
    public boolean balanced() {
        String s = this.toString();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '[':
                case '(':
                case '{':
                    stack.push(c);
                    break;
                case ']':
                    if (stack.isEmpty() || stack.pop() != '[') {
                        return false;
                    }
                    break;
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') {
                        return false;
                    }
                    break;
                case '}':
                    if (stack.isEmpty() || stack.pop() != '{') {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return stack.isEmpty();
    }

    /**
     * Checks if token list is empty
     *
     * @return true if Statement contains no tokens
     */
    public boolean isEmpty() {
        return tokens.isEmpty();
    }

    /**
     * Creates a String representation of the statement
     *
     * @return the Statement as a String
     */
    @Override
    public String toString() {
        String str = "";
        for (String t : tokens) {
            str += t + " ";
        }
        return str;
    }

    /**
     * Compares the given Statement to this Statement. Returns true if they are
     * the same object, or if they are different but contain the same Statements
     *
     * @param obj the other Statement to compare
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
        String str2 = ((Statement) obj).toString();

        return str1.equals(str2);
    }

    /**
     * Produces a hashCode representation of this Statement
     *
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.tokens);
        return hash;
    }

    /**
     * Compare this Statement to another Statement based on length
     *
     * @param other the other Statement to compare
     * @return -1 if less, 0 if equal, 1 if longer
     */
    @Override
    public int compareTo(Statement other) {
        // Statement objects will be compared by number of tokens
        // This makes lists of Statements sortable by length
        return new Integer(length()).compareTo(other.length());
    }

}
