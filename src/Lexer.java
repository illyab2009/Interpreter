/**
 * @author: Illya Balakin
 * Created on 02/24/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Lexer {
    private CodeScanner scanner;
    private String lexeme;
    private Token token;
    private int rowNumber;
    private int colNumber;

    /**
     * Constructs the Lexer
     * @param filePath cannot be null - checked by assertion
     * @throws FileNotFoundException
     */
    public Lexer(String filePath) throws FileNotFoundException {
        assert (filePath != null) : "filePath cannot be null";
        this.scanner = new CodeScanner(filePath);
    }

    /**
     * Gets the next lexeme and its rowNumber and colNumber from the scanner
     */
    private void setLexeme() {
        this.lexeme = this.scanner.getLexeme();
        this.rowNumber = this.scanner.getLineNumber();
        this.colNumber = this.scanner.getColNumber();
    }

    /**
     * Gets the next lexeme.
     * Gets the Token Type, creates the Token, and returns it.
     * @return token
     * @throws LexicalException
     */
    public Token getToken() throws LexicalException {
        setLexeme();
        token = new Token(getTokenType(), this.lexeme, this.rowNumber, this.colNumber);
        return token;
    }

    /**
     * Finds the tokenType of the current lexeme by testing
     * the lexeme against a series of if - else statements.
     * Returns the tokenType
     * @return tokType
     * @throws LexicalException
     */
    public TokenType getTokenType() throws LexicalException {
        if(lexeme == null || lexeme.length() == 0) {
            throw new IllegalArgumentException("invalid string argument");
        }

        TokenType tokType;

        // Checks if lexeme is a digit
        if(Character.isDigit(lexeme.charAt(0))) {

            for (int index = 0; index < lexeme.length(); index++) {
                if (!Character.isDigit(lexeme.charAt(index))) {
                    throw new LexicalException("literal integer expected  at row " + rowNumber
                            + " and column " + colNumber);
                }
            }

            tokType = TokenType.LIT_INT_TOK;
        }

        // If first character is a letter...
        else if(Character.isLetter(lexeme.charAt(0))) {
            if(lexeme.length() == 1) {
                tokType = TokenType.ID_TOK;
            } else if(lexeme.equals("function")) {
                tokType = TokenType.FUNC_TOK;
            } else if(lexeme.equals("if")) {
                tokType = TokenType.IF_TOK;
            } else if(lexeme.equals("then")) {
                tokType = TokenType.THEN_TOK;
            } else if(lexeme.equals("else")) {
                tokType = TokenType.ELSE_TOK;
            } else if(lexeme.equals("while")) {
                tokType = TokenType.WHILE_TOK;
            } else if (lexeme.equals("end")) {
                tokType = TokenType.END_TOK;
            } else if(lexeme.equals("do")) {
                tokType = TokenType.DO_TOK;
            } else if(lexeme.equals("print")) {
                tokType = TokenType.PRINT_TOK;
            } else if(lexeme.equals("repeat")) {
                tokType = TokenType.REPEAT_TOK;
            } else if(lexeme.equals("relative")) {
                tokType = TokenType.RELATIVE_TOK;
            } else if(lexeme.equals("until")) {
                tokType = TokenType.UNTIL_TOK;
            } else {
                throw new LexicalException("invalid lexeme at row " + rowNumber + " and column " + colNumber);
            }
        } else if(lexeme.equals("(")) {
            tokType = TokenType.L_PAREN_TOK;
        } else if(lexeme.equals(")")) {
            tokType = TokenType.R_PAREN_TOK;
        } else if(lexeme.equals(">=")) {
            tokType = TokenType.GE_TOK;
        } else if(lexeme.equals(">")) {
            tokType = TokenType.GT_TOK;
        } else if(lexeme.equals("<=")) {
            tokType = TokenType.LE_TOK;
        } else if(lexeme.equals("<")) {
            tokType = TokenType.LT_TOK;
        } else if(lexeme.equals("==")) {
            tokType = TokenType.EQ_TOK;
        } else if(lexeme.equals("~=")) {
            tokType = TokenType.NE_TOK;
        } else if(lexeme.equals("+")) {
            tokType = TokenType.ADD_TOK;
        } else if(lexeme.equals("-")) {
            tokType = TokenType.SUB_TOK;
        } else if(lexeme.equals("*")) {
            tokType = TokenType.MUL_TOK;
        } else if(lexeme.equals("/")) {
            tokType = TokenType.DIV_TOK;
        } else if(lexeme.equals("=")) {
            tokType = TokenType.ASSIGN_TOK;
        } else if(lexeme.equals("-1EOF")) {
            tokType = TokenType.EOF_TOK;
            lexeme = "?";
        } else {
            throw new LexicalException("invalid lexeme at row " + rowNumber + " and column " + colNumber);
        }

        return tokType;
    }

    /**
     * Gets the filePath from user, creates a Lexer.
     * Calls the Lexer until the EOF (End Of File)
     * and prints out the Line Number, Column Number, Token Type, and Lexeme.
     * @param args
     * @throws IOException
     * @throws LexicalException

    public static void main(String[] args) throws IOException, LexicalException {
        Scanner sc = new Scanner(System.in);
        boolean isEOF = false;

        System.out.print("Please enter the test program filepath: ");

        String filePath = sc.nextLine();

        Lexer lexer = new Lexer(filePath);
        System.out.println("Row# \tCol# \tToken");

        while(!isEOF) {
            Token token = lexer.getToken();
            System.out.println(token.getLineNumber() + "\t\t" + token.getColNumber() + "\t\t" + token.getTokenType() + ": " + token.getLexeme());
            if(token.getTokenType() == TokenType.EOF_TOK) {
                isEOF = true;
            }
        }
    }*/
}