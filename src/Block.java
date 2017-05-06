/**
 * @author: Illya Balakin
 * Created on 03/14/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */

import java.util.ArrayList;


public class Block {

    private ArrayList<Statement> statements;

    /**
     * Constructor
     * Creates a new Block object and initializes the statements ArrayList
     */
    public Block() {
        this.statements = new ArrayList<>();
    }

    /**
     * Adds input statement to the statements ArrayList
     * @param statement cannot be null - checked by assertion
     */
    public void add(Statement statement) {
        assert (statement != null) : "statement argument cannot be null";
        statements.add(statement);
    }


    /**
     * Executes every statement in the block
     */
    public void execute() {
        for (Statement statement : statements) {
            statement.execute();
        }
    }

}
