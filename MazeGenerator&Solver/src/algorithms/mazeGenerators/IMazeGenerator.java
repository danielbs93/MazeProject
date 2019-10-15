package algorithms.mazeGenerators;

/**
 * Created by Daniel Ben Simon
 */

public interface IMazeGenerator {
    public Maze generate(int r,int c);
    public long measureAlgorithmTimeMillis(int r, int c);
    /**
     * Interface for generating maze object
     * Must have: generate function - 2 variables (row,column) must be given to initialize maze's size
     *            measure function - calculate the time it takes to generate maze
     */
}
