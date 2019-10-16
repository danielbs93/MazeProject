package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SolutionDisplayer extends Displayer {

    private Solution solution;
    private StringProperty ImageFileSolution = new SimpleStringProperty();

    public void setSolution(Solution sol, Maze m) {
        super.setMaze(m);
        this.solution = sol;
        redraw();
    }

    @Override
    public void redraw() {
        if (maze != null) {
            try {
                Image SolutionImage = new Image(new FileInputStream(ImageFileSolution.get()));
                graphicsContext2D.clearRect(0, 0, getWidth(), getHeight());
                //drawing solution except the start and the goal positions
                if (solution != null && solution.getSolutionPath() != null) {
                    for (AState state : solution.getSolutionPath()) {
                        if (isGoal(state) || isStart(state))
                            continue;
                        else {
                            int row = ((MazeState) state).getPosition().getRowIndex();
                            int column = ((MazeState) state).getPosition().getColumnIndex();
                            Thread.sleep(100);
                            graphicsContext2D.drawImage(SolutionImage, column * cellHeight, row * cellWidth, cellHeight, cellWidth);
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param state
     * @return true if the state is the starting position in the maze
     */

    private boolean isStart(AState state) {
        return ((MazeState) state).getPosition().equals(maze.getStartPosition());
    }

    /**
     * @param state
     * @return true if the state is the goal position in the maze
     */

    private boolean isGoal(AState state) {
        return ((MazeState) state).getPosition().equals(maze.getGoalPosition());
    }

    //---GETTERS & SETTERS---


    public Solution getSolution() {
        return solution;
    }


    public String getImageFileSolution() {
        return ImageFileSolution.get();
    }

    public StringProperty imageFileSolutionProperty() {
        return ImageFileSolution;
    }

    public void setImageFileSolution(String imageFileSolution) {
        this.ImageFileSolution.set(imageFileSolution);
    }

    //---CLEAR SOLUTION FOR NEW MAZE---
    public void clearSolution() {
        if (graphicsContext2D != null)
            graphicsContext2D.clearRect(0,0,cellHeight,cellWidth);
    }

}
