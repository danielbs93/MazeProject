package algorithms.mazeGenerators;

/**
 * Created by Daniel Ben Simon
 */

public abstract class AMazeGenerator implements IMazeGenerator {
    protected Maze iMaze;

    /*
    Measure the time it takes to generate a maze
     */

    @Override
    public long measureAlgorithmTimeMillis(int r, int c) {
        long start = System.currentTimeMillis();
        iMaze = generate(r,c);
        long end = System.currentTimeMillis();
        return (end-start);
    }
}
