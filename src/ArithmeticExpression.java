/*
 * @author: Illya Balakin
 * Created on 03/14/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */


public class ArithmeticExpression {

    private ArithmeticExpressionType expressionType;
    private ArithmeticOperator operator;
    private ArithmeticExpression expression1, expression2;
    private char id;
    private int value;


    /**
     * Constructor
     * Creates a Basic Arithmetic Expression version of an Arithmetic Expression
     * Sets expressionType to BASIC_ARITH_EXPR
     * @param operator cannot be null
     * @param expression1 cannot be null
     * @param expression2 cannot be null
     */
    public ArithmeticExpression(ArithmeticOperator operator, ArithmeticExpression expression1,
                                ArithmeticExpression expression2) {
        assert(operator == null) : "Arithmetic operator cannot be null";
        assert(expression1 == null || expression2 == null) : "Arithmetic expression cannot be null";

        this.expressionType = ArithmeticExpressionType.BASIC_ARITH_EXPR;
        this.operator = operator;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }


    /**
     * Constructor
     * Creates an ID version of an Arithmetic Expression
     * Sets expressionType to ID_EXPR
     * @param id cannot be null
     */
    public ArithmeticExpression(char id) {
        assert (!Character.isLetter(id)) : "ID must be a single letter";
        this.expressionType = ArithmeticExpressionType.ID_EXPR;
        this.id = id;
    }


    /**
     * Constructor
     * Creates Literal Integer version of an Arithmetic Expression
     * Sets expressionType to LIT_INT_EXPR
     * @param value
     */
    public ArithmeticExpression(int value) {
        this.expressionType = ArithmeticExpressionType.LIT_INT_EXPR;
        this.value = value;
    }

    /**
     * Returns character of the ID version of the ArithmeticObject
     * expressionType must be ID_EXPR - checked by assertion
     * @return id
     */
    public char getIdChar() {
        assert (expressionType == ArithmeticExpressionType.ID_EXPR);
        return this.id;
    }



    /**
     * Evaluates basic expression
     * @return value of basic expression (int)
     */
    private int evaluateBasicExpression() {
        int value = 0;

        switch(operator) {
            case MUL_OP: value = expression1.evaluate() * expression2.evaluate();
                break;
            case DIV_OP: value = expression1.evaluate() / expression2.evaluate();
                break;
            case ADD_OP: value = expression1.evaluate() + expression2.evaluate();
                break;
            case SUB_OP: value = expression1.evaluate() * expression2.evaluate();
                break;
        }
        return value;
    }



    /**
     * @return value of id (int)
     */
    private int evaluateID() {
        return AssignmentMemory.getValue(id);
    }



    /**
     * @return value of literal integer
     */
    private int evaluateLiteralInteger() {
        return this.value;
    }




    public int evaluate() {
        int value = 0;
        switch(this.expressionType) {
            case BASIC_ARITH_EXPR: value = evaluateBasicExpression(); break;
            case ID_EXPR: value = evaluateID(); break;
            case LIT_INT_EXPR: value = evaluateLiteralInteger(); break;
        }
        return value;
    }
}
