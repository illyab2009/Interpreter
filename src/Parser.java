/**
 * @author: Illya Balakin
 * Created on 03/14/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */

import java.io.FileNotFoundException;

public class Parser {
    private Lexer lexer;
    private Token token;

    /**
     * Constructor for the Parser
     * Takes a filePath as an input string, and creates a lexer object by passing the filepath to the lexer
     * @param filePath is the input file path for the Parser
     * @throws FileNotFoundException
     */
    public Parser(String filePath) throws FileNotFoundException {
        this.lexer = new Lexer(filePath);
    }

    /**
     * Creates and returns program object by getting the main block of the program and making sure
     * the program's syntax is correct.
     * @return Program object
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    public Program getProgram() throws LexicalException, ParserException {
        setToken();
        compare(this.token, TokenType.FUNC_TOK);

        setToken();
        ArithmeticExpression functionId = getArithmeticExpression(ArithmeticExpressionType.ID_EXPR);

        setToken();
        compare(this.token, TokenType.L_PAREN_TOK);
        setToken();
        compare(this.token, TokenType.R_PAREN_TOK);

        Block block = getBlock();

        // getBlock sets end token
        compare(this.token, TokenType.END_TOK);

        setToken(); // set EOF_TOK
        if(this.token.getTokenType() != TokenType.EOF_TOK) {
            throw new ParserException("Final token is not EOF token");
        }
        return new Program(functionId.getIdChar(), block);
    }

    /**
     * Creates a block object and adds all of the needed statements to the block.
     * @return Block
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private Block getBlock() throws LexicalException, ParserException {
        Block block = new Block();
        setToken();
        while (this.token.getTokenType() == TokenType.IF_TOK ||
                this.token.getTokenType() == TokenType.WHILE_TOK ||
                this.token.getTokenType() == TokenType.ID_TOK ||
                this.token.getTokenType() == TokenType.REPEAT_TOK ||
                this.token.getTokenType() == TokenType.PRINT_TOK) {
            Statement statement = getStatement();
            block.add(statement);
            setToken();
        }
        return block;
    }

    /**
     * Creates the necessary statement object and returns it
     * @return Statement
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private Statement getStatement() throws LexicalException, ParserException {
        // Block sets new token for all statements

        if (token.getTokenType() == TokenType.IF_TOK) {
            return getIfStatement();
        }
        else if (token.getTokenType() == TokenType.WHILE_TOK) {
            return getWhileStatement();
        }
        else if (token.getTokenType() == TokenType.ID_TOK) {
            return getAssignmentStatement();
        }
        else if (token.getTokenType() == TokenType.REPEAT_TOK) {
            return getRepeatStatement();
        }
        else if (token.getTokenType() == TokenType.PRINT_TOK) {
            return getPrintStatement();
        } else {
            throw new ParserException("statement expected in line " + this.token.getLineNumber() +
                    " and column " + this.token.getColNumber());
        }
    }

    /**
     * Creates and returns an "If-Statement" version of a statement object
     * @return Statement
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private Statement getIfStatement() throws LexicalException, ParserException {
        //currect token is if
        compare(this.token, TokenType.IF_TOK);
        setToken();
        BooleanExpression expression = getBooleanExpression();
        setToken();
        compare(this.token, TokenType.THEN_TOK);
        Block block1 = getBlock();
        compare(this.token, TokenType.ELSE_TOK);
        Block block2 = getBlock();
        compare(this.token, TokenType.END_TOK);
        return new Statement(expression, block1, block2);
    }

    /**
     * Creates and returns a "While-Statement" version of a statement object
     * @return Statement
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private Statement getWhileStatement() throws LexicalException, ParserException {
        //currect token is while
        compare(this.token, TokenType.WHILE_TOK);
        setToken();
        BooleanExpression expression = getBooleanExpression();
        setToken();
        compare(this.token, TokenType.DO_TOK);
        Block block = getBlock();
        compare(this.token, TokenType.END_TOK);
        return new Statement(expression, block);
    }

    /**
     * Creates and returns an "Assignment-Statement" version of a statement object
     * @return Statement
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private Statement getAssignmentStatement()  throws LexicalException, ParserException {
        //current token is ID
        ArithmeticExpression variableID = getArithmeticExpression(getArithmeticExpressionType());
        setToken();
        compare(this.token, TokenType.ASSIGN_TOK);
        setToken();
        ArithmeticExpression expression = getArithmeticExpression(getArithmeticExpressionType());
        return new Statement(variableID, expression);
    }


    /**
     * Creates and returns a "Repeat-Statement" version of a statement object
     * @return Statement
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private Statement  getRepeatStatement()  throws LexicalException, ParserException {
        //current token is repeat
        compare(this.token, TokenType.REPEAT_TOK);
        Block block = getBlock(); //sets until token
        compare(this.token, TokenType.UNTIL_TOK);
        setToken();
        BooleanExpression expression = getBooleanExpression();
        return new Statement(block, expression);
    }

    /**
     * Creates and returns a "Print-Statement" version of a statement object
     * @return Statement
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private Statement getPrintStatement()  throws LexicalException, ParserException {
        //current token is print
        compare(this.token, TokenType.PRINT_TOK);
        setToken();
        compare(this.token, TokenType.L_PAREN_TOK);
        setToken();
        ArithmeticExpression expression = getArithmeticExpression(getArithmeticExpressionType());
        setToken();
        compare(this.token, TokenType.R_PAREN_TOK);
        return new Statement(expression);
    }

    /**
     * Creates and returns a Boolean Expression object
     * @return BooleanExpression
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private BooleanExpression getBooleanExpression()  throws LexicalException, ParserException {
        //current token is a relative operator
        RelativeOperator operator = getRelativeOperator();
        setToken();

        ArithmeticExpression expression1 = getArithmeticExpression(getArithmeticExpressionType());
        setToken();
        ArithmeticExpression expression2 = getArithmeticExpression(getArithmeticExpressionType());
        return new BooleanExpression(operator, expression1, expression2);
    }

    /**
     * Checks if current token is a RelativeOperator.
     * If so, this method decides what RelativeOperator the token is, and returns it.
     * @return RelativeOperator
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private RelativeOperator getRelativeOperator()  throws LexicalException, ParserException {
        //current token is operator
        RelativeOperator operator;
        if(this.token.getTokenType() == TokenType.LE_TOK) {
            operator = RelativeOperator.LE_OP;
        } else if (this.token.getTokenType() == TokenType.LT_TOK) {
            operator = RelativeOperator.LT_OP;
        } else if (this.token.getTokenType() == TokenType.GE_TOK) {
            operator = RelativeOperator.GE_OP;
        } else if (this.token.getTokenType() == TokenType.GT_TOK) {
            operator = RelativeOperator.GT_OP;
        } else if (this.token.getTokenType() == TokenType.EQ_TOK) {
            operator = RelativeOperator.EQ_OP;
        } else if (this.token.getTokenType() == TokenType.NE_TOK) {
            operator = RelativeOperator.NE_OP;
        } else {
            throw new ParserException("relative operator expected in line " + this.token.getLineNumber() +
                    " and column " + this.token.getColNumber());
        }
        return operator;
    }


    /**
     * Finds the ArithmeticExpressionType by checking the current token,
     * and returns the ArithmeticExpressionType
     * @return ArithmeticExpressionType
     * @throws ParserException if current token is not an Id, Lit Int, or Arithmetic Operator.
     */
    private ArithmeticExpressionType getArithmeticExpressionType() throws ParserException {
        ArithmeticExpressionType type;

        if(this.token.getTokenType() == TokenType.ID_TOK) {
            type = ArithmeticExpressionType.ID_EXPR;
        } else if(this.token.getTokenType() == TokenType.LIT_INT_TOK) {
            type = ArithmeticExpressionType.LIT_INT_EXPR;
        } else if (tokenIsArithmeticOperator()) {
            type = ArithmeticExpressionType.BASIC_ARITH_EXPR;
        } else {
            throw new ParserException("Arithmetic Expression expected at line " + this.token.getLineNumber() +
                    " and column " + this.token.getColNumber());
        }
        return type;
    }
    /**
     * Decides what kind of Arithmetic Expression is needed,
     * creates and returns the ArithmeticExpression object
     * @return ArithmeticExpression object
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private ArithmeticExpression getArithmeticExpression(ArithmeticExpressionType type)
            throws LexicalException, ParserException {

        ArithmeticExpression expression;
        switch(type) {
            case ID_EXPR:
                expression = new ArithmeticExpression(this.token.getLexeme().charAt(0));
                break;

            case LIT_INT_EXPR:
                // current token is Lit Int
                int value = Integer.parseInt(this.token.getLexeme());
                expression = new ArithmeticExpression(value);
                break;

            case BASIC_ARITH_EXPR:
                ArithmeticOperator operator = getArithmeticOperator();
                setToken();
                ArithmeticExpression expression1 = getArithmeticExpression(getArithmeticExpressionType());
                setToken();
                ArithmeticExpression expression2 = getArithmeticExpression(getArithmeticExpressionType());
                expression = new ArithmeticExpression(operator, expression1, expression2);
                break;

            default:
                throw new ParserException("arithmetic expression expected in line " + this.token.getLineNumber() +
                        " and column " + this.token.getColNumber());
        }
        return expression;
    }


    /**
     * Checks if current token is an Arithmetic Operator
     * @return true if current token is an arithmetic operator
     *         false if current token is NOT an arithmetic operator
     */
    private boolean tokenIsArithmeticOperator() {
        if(this.token.getTokenType() == TokenType.ADD_TOK ||
                this.token.getTokenType() == TokenType.SUB_TOK ||
                this.token.getTokenType() == TokenType.MUL_TOK ||
                this.token.getTokenType() == TokenType.DIV_TOK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks the current token, decides what kind of Arithmetic Operator it is,
     * and returns the Arithmetic Operator object
     * @return ArithmeticOperator object
     * @throws LexicalException if lexical error occurred
     * @throws ParserException if parsing error occurred
     */
    private ArithmeticOperator getArithmeticOperator()  throws LexicalException, ParserException {
        //current token is operator
        if(this.token.getTokenType() == TokenType.ADD_TOK) {
            return ArithmeticOperator.ADD_OP;
        } else if (this.token.getTokenType() == TokenType.SUB_TOK) {
            return ArithmeticOperator.SUB_OP;
        } else if (this.token.getTokenType() == TokenType.MUL_TOK) {
            return ArithmeticOperator.MUL_OP;
        } else if (this.token.getTokenType() == TokenType.DIV_TOK) {
            return ArithmeticOperator.DIV_OP;
        } else {
            throw new ParserException("arithmetic operator expected in line " + this.token.getLineNumber() +
                    " and column " + this.token.getColNumber());
        }
    }

    /**
     * Compares the tokenType of an input token object with an input tokenType
     * If the two tokenTypes do not match, an exception is thrown
     * @param token cannot be null
     * @param tokenType cannot be null
     * @throws ParserException if parsing error occurred
     */
    private void compare(Token token, TokenType tokenType) throws ParserException {
        assert (token != null) : "token argument cannot be null";
        assert (tokenType != null) : "token argument cannot be null";

        if (token.getTokenType() != tokenType) {
            throw new ParserException(tokenType + " expected in line " + token.getLineNumber() +
                    " and column " + token.getColNumber());
        }
    }

    /**
     * Sets global token to the next token in the input stream.
     * @throws LexicalException if lexical error occurred
     */
    private void setToken() throws LexicalException {
        this.token = this.lexer.getToken();
    }
}
