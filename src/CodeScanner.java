/**
 * @author: Illya Balakin
 * Created on 02/24/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CodeScanner {
    private int lineNumber;
    private int colNumber;
    private int index;
    private Scanner input;
    private String currentLine;
    private boolean endOfFile = false;

    /**
     * Constructs the CodeScanner
     * @param filePath
     * @throws FileNotFoundException
     */
    public CodeScanner(String filePath) throws FileNotFoundException {
        if(filePath == null) {
            throw new IllegalArgumentException("null file name argument");
        }

        input = new Scanner(new File(filePath));
        setNewLine();
        lineNumber = 1;
        colNumber = 0;
        index = 0;

    }

    /**
     * Skips any whitespace before next lexeme.
     * Increases index to the next available lexeme.
     */
    private void skipWhitespace() {
        while(index < currentLine.length() && Character.isWhitespace(currentLine.charAt(index))) {
            index++;
        }
    }

    /**
     * Skips any whitespace, loops through the input file's characters,
     * creates the lexeme and returns it.
     * @return lexeme (String)
     */
    public String getLexeme() {
        String lexeme = "";

        skipWhitespace();

        // Sets new line if current line is finished.
        if(index == currentLine.length()) {
            index = 0;
            setNewLine();
        }

        colNumber = index + 1; // sets colNumber

        // loops through line and creates the lexeme
        while (index < currentLine.length() && !Character.isWhitespace(currentLine.charAt(index))) {
            lexeme = lexeme + currentLine.charAt(index);
            index++;
        }

        // if the end of file is reached, "-1EOF" is returned as the lexeme.
        if(endOfFile) {
            return "-1EOF";
        }
        else {
            return lexeme;
        }
    }

    /**
     * Sets new currentLine
     */
    private void setNewLine() {
        if(input.hasNext()) {
            currentLine = input.nextLine();
            skipWhitespace();

            if(currentLine.isEmpty()) {
                setNewLine();
            }
        }
        else {
            endOfFile = true;
        }
        lineNumber++;
    }

    //returns Line Number
    public int getLineNumber() {
        return this.lineNumber;
    }

    //returns column (row) Number
    public int getColNumber() {
        return this.colNumber;
    }
}