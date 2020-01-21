/**
 * Artificial Intelligence, CSCI 580
 * Spring 2019
 *
 * NO NEED TO EDIT (except while debugging?)
 *
 * @author Patrick Donnelly
 */
package geneticcode;

import static geneticcode.Factory.getRandomVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A collection of static methods to generate different program elements.
 *
 */
public class Factory {

    // List of all keywords
    private static final List<String> keywords = Arrays.asList("bool", "break;", "char", "continue;", "const", "do", "double", "else", "false", "float", "for", "if", "int", "long", "void", "return", "short", "signed", "static", "true", "unsigned", "while");

    // List of types
    private static final List<String> types = Arrays.asList("bool", "char", "double", "float", "int", "long", "short");

    // List of type modifiers
    private static final List<String> modifiers = Arrays.asList("const", "signed", "static", "unsigned");

    // List of keywords involved in branching
    private static final List<String> branch = Arrays.asList("break;", "continue;", "return;");

    // List of common binary operators
    private static final List<String> binary_operators = Arrays.asList("+", "-", "*", "/", "%", "==", "!=", ">", "<", ">=", "<=", "&&", "||", "&", "|");

    // List of unary operators
    private static final List<String> unary_operators = Arrays.asList("-", "!");

    // Limit ourselves to six variable names
    private static final List<String> variables = Arrays.asList("i", "j", "k", "x", "y", "z", "a", "b", "c", "m", "n", "p");

    // List of characters
    private final static String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final static int N = alphabet.length();

    // Random seed 
    private static final Random r = new Random();

    // Dummy constructor to prevent object instances
    private Factory() {
    }

    /**
     * Returns a random token: keyword, value, variable, or operator
     *
     * @return the random keyword
     */
    public static String getRandomToken() {
        double rnd = r.nextDouble();
        // Coin toss
        if (rnd < 0.5) {
            return keywords.get(r.nextInt(keywords.size()));
        } else if (rnd < 0.6) {
            return getRandomOperator();
        } else if (rnd < 0.8) {
            return getRandomVariable();
        } else {
            return getRandomValue();
        }
    }

    /**
     * Returns a random keyword
     *
     * @return the random keyword
     */
    public static String getRandomKeyword() {
        return keywords.get(r.nextInt(keywords.size()));
    }

    /**
     * Returns a random number
     *
     * @return a new number
     */
    public static String getRandomValue() {
        double rnd = r.nextDouble();
        String neg = rnd < 0.1 ? "-" : "";

        rnd = r.nextDouble();
        // Coin toss, either an int or a double
        if (rnd < 0.5) {
            return String.format("%s%.2f", neg, (r.nextDouble() * 200) - 100);
        } else if (rnd < 0.9) {
            return "" + neg + (r.nextInt(200) - 100);
        } else {
            return "'" + (alphabet.charAt(r.nextInt(N))) + "'";
        }
    }

    /**
     * Returns a random variable
     *
     * @return a new variable
     */
    public static String getRandomVariable() {
        return variables.get(r.nextInt(variables.size()));
    }

    /**
     * Returns a random variable
     *
     * @return a random variable
     */
    public static String getRandomValueOrVariable() {
        double rnd = r.nextDouble();
        // Coin toss, either a number or variable
        if (rnd < 0.5) {
            return getRandomValue();
        } else {
            rnd = r.nextDouble();
            // 2nd Coin toss, add prefix or postfix
            if (rnd < 0.1) {
                double rnd2 = r.nextDouble();
                if (rnd2 < 0.25) {
                    return "++" + getRandomVariable();
                } else if (rnd2 < 0.5) {
                    return "--" + getRandomVariable();
                } else if (rnd2 < 0.75) {
                    return getRandomVariable() + "++";
                } else {
                    return getRandomVariable() + "--";
                }
            } else {
                String not = r.nextDouble() < 0.1 ? "!" : "";
                return not + getRandomVariable();
            }
        }
    }

    /**
     * Returns a random operator
     *
     * @return the random operator
     */
    public static String getRandomOperator() {
        return binary_operators.get(r.nextInt(binary_operators.size()));
    }

    /**
     * Returns a random type
     *
     * @return the random type
     */
    public static String getRandomType() {
        return types.get(r.nextInt(types.size()));
    }

    /**
     * Returns a random expression
     *
     * @return a random expression
     */
    public static ArrayList<String> getRandomExpression() {
        ArrayList<String> lst = new ArrayList<>();
        int len = r.nextInt(r.nextInt(8) + 1);
    
        boolean parenthesis = len > 1 ? (r.nextDouble() < 0.33) : false;       
        
        if (parenthesis){
            lst.add("(");
        }
        lst.add(getRandomValueOrVariable());
        for (int i = 1; i < len; i++) {
            lst.add(getRandomOperator());
            double rnd = r.nextDouble();
            if (rnd < 0.5) {
                lst.add(getRandomValueOrVariable());
            }else{
                lst.addAll(getRandomExpression());
            }
        }
        if (parenthesis){
            lst.add(")");
        }
        return lst;

    }

    /**
     * Returns a random declaration
     *
     * @return the random declaration
     */
    public static ArrayList<String> getRandomDeclaration() {
        ArrayList<String> lst = new ArrayList<>();
        double rnd = r.nextDouble();
        if (rnd < 0.25) {
            lst.add(modifiers.get(r.nextInt(modifiers.size())));
        }

        lst.add(getRandomType());
        lst.add(getRandomVariable());

        rnd = r.nextDouble();
        if (rnd < 0.1) {
            lst.add("=");
            lst.addAll(getRandomExpression());
            lst.add("?");
            lst.add(getRandomValueOrVariable());
            lst.add(":");
            lst.add(getRandomValueOrVariable());
        } else if (rnd < 0.6) {
            lst.add("=");
            lst.addAll(getRandomExpression());
        }
        lst.add(";");
        return lst;
    }

    /**
     * Returns a random assignment statement
     *
     * @return an assignment statement
     */
    public static ArrayList<String> getRandomAssignment() {
        ArrayList<String> lst = new ArrayList<>();
        lst.add(getRandomVariable());
        lst.add("=");
        lst.addAll(getRandomExpression());
        lst.add(";");
        return lst;
    }

    /**
     * Returns a random control statement
     *
     * @return an assignment control
     */
    public static ArrayList<String> getRandomControlStatement() {
        ArrayList<String> lst = new ArrayList<>();

        double rnd = r.nextDouble();
        if (rnd < 0.3) {
            // if statement
            lst.add("if");
            lst.add("(");
            lst.addAll(getRandomExpression());
            lst.add(")");
            lst.addAll(getRandomBlock());
        } else if (rnd < 0.5) {
            // for loop
            lst.add("for");
            lst.add("(");
            lst.addAll(getRandomDeclaration());
            lst.addAll(getRandomExpression());
            lst.add(";");
            lst.addAll(getRandomExpression());
            lst.add(")");
            lst.addAll(getRandomBlock());
        } else if (rnd < 0.7) {
            // while loop
            lst.add("while");
            lst.add("(");
            lst.addAll(getRandomExpression());
            lst.add(")");
            lst.addAll(getRandomBlock());
        } else if (rnd < 0.9) {
            // do while loop
            lst.add("do");
            lst.addAll(getRandomBlock());
            lst.add("while");
            lst.add("(");
            lst.addAll(getRandomExpression());
            lst.add(")");
            lst.add(";");
        } else {
            // switch
            lst.add("switch");
            lst.add("(");
            lst.add(getRandomVariable());
            lst.add(")");
            lst.add("{");
            int nCases = r.nextInt(r.nextInt(10) + 1);
            for (int i = 0; i < nCases; i++) {
                lst.add("case");
                lst.add(getRandomValue());
                lst.add(":");
                lst.addAll(getRandomBlock());
                rnd = r.nextDouble();
                if (rnd < 0.5) {
                    lst.add("break;");
                }
            }
            rnd = r.nextDouble();
            if (rnd < 0.25) {
                lst.add("default");
                lst.add(":");
                lst.addAll(getRandomBlock());
            }
            lst.add("}");
        }
        return lst;
    }

    /**
     * Returns a random control statement
     *
     * @return an assignment control
     */
    public static ArrayList<String> getRandomBlock() {
        ArrayList<String> lst = new ArrayList<>();
        int len = r.nextInt(r.nextInt(6) + 1) + 1;

        lst.add("{");
        for (int i = 0; i < len; i++) {
            double rnd = r.nextDouble();
            if (rnd < 0.2) {
                lst.addAll(getRandomDeclaration());
            } else if (rnd < 0.8) {
                lst.addAll(getRandomExpression());
                lst.add(";");
            } else if (rnd < 0.9) {
                lst.add(branch.get(r.nextInt(branch.size())));
            } else {
                lst.addAll(getRandomControlStatement());
            }
        }
        lst.add("}");
        return lst;
    }

    /**
     * Returns a statement
     *
     * @return a statement
     */
    public static Statement getRandomStatement() {
        ArrayList<String> lst = new ArrayList<>();

        double rnd = r.nextDouble();
        if (rnd < 0.25) {
            lst.addAll(getRandomDeclaration());
        } else if (rnd < 0.5) {
            lst.addAll(getRandomAssignment());
        } else if (rnd < 0.75) {
            lst.addAll(getRandomControlStatement());
        } else {
            lst.addAll(getRandomControlStatement());
        }

        Statement statement = new Statement(lst);
        return statement;
    }

    /**
     * Generate a random list of statements
     *
     * @param kStatements the number of statements to add
     * @return a new Program
     */
    public static Program makeRandomProgram(int kStatements) {
        ArrayList<Statement> statements = new ArrayList<>();

        int nDeclarations = (int) Math.round(kStatements * 0.25);
        for (int i = 0; i < nDeclarations; i++) {
            statements.add(new Statement(getRandomDeclaration()));
        }

        for (int k = nDeclarations; k < kStatements; k++) {
            statements.add(getRandomStatement());
        }
        return new Program(statements);
    }
}
