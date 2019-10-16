package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;

public interface IModel {
    void generate2DMaze(int rows, int columns);
    void startServers();
    void Stop();
    Maze getMaze();
    int getPlayerRow();
    int getPlayerColumn();
    void solve2DMaze();
    Solution getSolution();
    void PlayerisMoving(KeyCode direction);
    void SaveMaze(File file);
    void LoadMaze(File file);
    boolean isStartPosition(int row, int col);
    boolean isGoalPosition(int row, int col);
    boolean isWallPosition();
}
