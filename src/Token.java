/**
 * @author: Illya Balakin
 * Created on 02/24/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */

public class Token {
    private TokenType tokenType;
    private String lexeme;
    private int lineNumber;
    private int colNumber;

    public Token (TokenType tokenType, String lexeme, int rowNumber, int colNumber) {
        if (tokenType == null || (lexeme == null || lexeme.length() == 0) || rowNumber <= 0 || colNumber <= 0) {
            throw new IllegalArgumentException("One of the TOKEN attributes is empty (NULL)");
        }
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.lineNumber = rowNumber;
        this.colNumber = colNumber;
    }

    public TokenType getTokenType()
    {
        return tokenType;
    }

    public String getLexeme()
    {
        return lexeme;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public int getColNumber()
    {
        return colNumber;
    }

}
