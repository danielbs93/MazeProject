package algorithms.mazeGenerators;

/**
 * Created by Daniel Ben Simon
 */

public class EmptyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int r, int c) {
        iMaze = new Maze(r,c);
        return iMaze;
    }
}
