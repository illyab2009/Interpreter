/**
 * @author: Illya Balakin
 * Created on 03/15/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */

public class AssignmentMemory {

    // Static variable that holds the various ID values
    private static int[] lowerCase = new int[26];
    private static int[] upperCase = new int[26];


    /**
     * Adds value to static array based on the passed ID
     * @param id must me a letter
     * @param value cannot be null
     */
    public static void add(char id, int value) {
        assert (Character.isLetter(id)) : "ID character argument must be a letter";
        int index;

        if(Character.isLowerCase(id)) {
            index = ((int) id) - 97;
            lowerCase[index] = value;
        }
        else {
            index = ((int) id) - 65;
            upperCase[index] = value;
        }
    }

    /**
     * @param id - must be a letter
     * @return value of the passed ID
     */
    public static int getValue(char id) {
        assert (Character.isLetter(id)) : "ID character argument must be a letter";
        int index;
        int value;
        if(Character.isLowerCase(id)) {
            index = ((int) id) - 97;
            value = lowerCase[index];
        }
        else {
            index = ((int) id) - 65;
            value = upperCase[index];
        }
        return value;
    }
}
