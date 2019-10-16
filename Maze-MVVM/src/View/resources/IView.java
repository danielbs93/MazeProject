package View.resources;


import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface IView {

    void MazeDisplayer(Maze maze);
    void SolutionDisplayer(Solution solution, Maze maze);
    void PlayerDisplayer(int characterPositionRow, int characterPositionColumn, Maze maze);
}
