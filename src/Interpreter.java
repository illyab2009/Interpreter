/**
 * @author: Illya Balakin
 * Created on 03/16/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Interpreter {

    public static void main(String[] args)
            throws FileNotFoundException, LexicalException, ParserException {

        Scanner sc = new Scanner(System.in);

        System.out.print("Please enter the test program filepath: ");

        String filePath = sc.nextLine();
        Parser parser = new Parser(filePath);
        Program program = parser.getProgram();
        program.execute();

        //System.exit(0);
    }
}
