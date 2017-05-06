/**
 * @author: Illya Balakin
 * Created on 03/14/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */

public class Statement {

    /**
     * This documents StatementType
     */
    private enum StatementType {
        IF, WHILE, ASSIGN, REPEAT, PRINT
    }

    private StatementType statementType;
    private BooleanExpression boolExpression;
    private Block block1, block2;
    private ArithmeticExpression variable, arithExpression;

    /**
     * Creates the If-Statement version of the Statement object
     * @param expression cannot be null
     * @param block1 cannot be null
     * @param block2 cannot be null
     * Checked by assertion
     */
    public Statement (BooleanExpression expression, Block block1, Block block2) {
        assert (expression != null) : "Boolean Expression argument cannot be null";
        assert (block1 != null || block2 != null) : "Block argument cannot be null";

        this.statementType = StatementType.IF;
        this.boolExpression = expression;
        this.block1 = block1;
        this.block2 = block2;
    }

    /**
     * Creates the While-Statement version of the Statement object
     * @param expression cannot be null
     * @param block cannot be null
     * Checked by assertion
     */
    public Statement (BooleanExpression expression, Block block) {
        assert (expression != null) : "Boolean Expression argument cannot be null";
        assert (block != null) : "Block argument cannot be null";

        this.statementType = StatementType.WHILE;
        this.boolExpression = expression;
        this.block1 = block;
    }

    /**
     * Creates the Assignment-Statement version of the Statement object
     * @param variable cannot be null
     * @param expression cannot be null
     * Checked by assertion
     */
    public Statement (ArithmeticExpression variable, ArithmeticExpression expression) {
        assert (variable != null) : "ID argument cannot be null";
        assert (expression != null) : "Arithmetic Expression argument cannot be null";

        this.statementType = StatementType.ASSIGN;
        this.variable = variable;
        this.arithExpression = expression;
    }

    /**
     * Creates the Repeat-Statement version of the Statement object
     * @param block cannot be null
     * @param expression cannot be null
     * Checked by assertion
     */
    public Statement (Block block, BooleanExpression expression) {
        assert (block != null) : "Block argument cannot be null";
        assert (expression != null) : "Boolean Expression argument cannot be null";

        this.statementType = StatementType.REPEAT;
        this.block1 = block;
        this.boolExpression = expression;
    }

    /**
     * Creates the Print-Statement version of the Statement object
     * @param expression cannot be null
     * Checked by assertion
     */
    public Statement (ArithmeticExpression expression) {
        assert (expression != null) : "Arithmetic Expression argument cannot be null";

        this.statementType = StatementType.PRINT;
        this.arithExpression = expression;
    }


    /**
     * Executes the If-Statement version of the Statement
     */
    private void executeIfStatement() {
        if(boolExpression.evaluate()) {
            block1.execute();
        } else {
            block2.execute();
        }
    }


    /**
     * Executes the While-Statement version of the Statement
     */
    private void executeWhileStatement() {
        while(boolExpression.evaluate()) {
            block1.execute();
        }
    }

    /**
     * Executes the Assignment-Statement version of the Statement
     */
    private void executeAssignmentStatement() {
        AssignmentMemory.add(variable.getIdChar(), arithExpression.evaluate());
    }

    /**
     *  Executes the Repeat-Statement version of the Statement
     */
    private void executeRepeatStatement() {
        block1.execute();
        while(!boolExpression.evaluate()) {
            block1.execute();
        }
    }

    /**
     *  Prints the Print-Statement version of the Statement
     */
    private void executePrintStatement() {
        System.out.print(arithExpression.evaluate());
    }

    /**
     * Checks the type of the current Statement, and calls the proper execute method
     */
    public void execute() {
        switch(statementType) {
            case IF: executeIfStatement(); break;
            case WHILE: executeWhileStatement(); break;
            case ASSIGN: executeAssignmentStatement(); break;
            case REPEAT: executeRepeatStatement(); break;
            case PRINT: executePrintStatement(); break;
        }
    }
}
