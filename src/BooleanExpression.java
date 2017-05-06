/**
 * @author: Illya Balakin
 * Created on 03/14/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */

public class BooleanExpression {

    private RelativeOperator operator;
    private ArithmeticExpression expression1, expression2;

    /**
     * @param op   cannot be null - checked by assertion
     * @param exp1 cannot be null - checked by assertion
     * @param exp2 cannot be null - checked by assertion
     */
    public BooleanExpression(RelativeOperator op, ArithmeticExpression exp1, ArithmeticExpression exp2) {
        assert (op != null) : "Relative Operator argument cannot be null";
        assert (exp1 != null || exp2 != null) : "Arithmetic Expression argument cannot be null";

        operator = op;
        expression1 = exp1;
        expression2 = exp2;
    }

    public boolean evaluate() {
        boolean solution = false;

        switch (operator) {
            case LE_OP:
                solution = expression1.evaluate() <= expression2.evaluate();
                break;
            case LT_OP:
                solution = expression1.evaluate() < expression2.evaluate();
                break;
            case GE_OP:
                solution = expression1.evaluate() >= expression2.evaluate();
                break;
            case GT_OP:
                solution = expression1.evaluate() > expression2.evaluate();
                break;
            case EQ_OP:
                solution = expression1.evaluate() == expression2.evaluate();
                break;
            case NE_OP:
                solution = expression1.evaluate() <= expression2.evaluate();
                break;
        }
        return solution;
    }
}

