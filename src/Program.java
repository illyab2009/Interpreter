/**
 * @author: Illya Balakin
 * Created on 03/14/2017
 * CS4308 Section 01
 * Project - 3rd Deliverable - Interpreter
 */

public class Program {
    private char functionID;
    private Block block;

    /**
     * Constrctor - creates a Program object
     * @param block cannot be null - checked by assertion
     * @param id cannot be null - checked by assertion
     */
    public Program (char id, Block block) {
        assert(block != null) : "block argument cannot be null";
        assert(id != 0) : "id argument cannot be null";
        this.functionID = id;
        this.block = block;
    }

    /**
     * @return functionID
     */
    public char getFunctionID() {
        return this.functionID;
    }

    public void execute() {
        block.execute();
    }

}
