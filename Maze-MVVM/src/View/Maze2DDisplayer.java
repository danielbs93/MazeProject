package View;

import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Maze2DDisplayer extends Displayer implements MazeDisplayer {

    @FXML
    private StringProperty ImageFileWall = new SimpleStringProperty();
    private StringProperty ImageFileWalk = new SimpleStringProperty();
    private StringProperty ImageFileStart = new SimpleStringProperty();
    private StringProperty ImageFileGoal = new SimpleStringProperty();

    private PlayerDisplayer player = null;
    //---Initializing---

    public void Maze2DDisplayer(Maze m) {
        super.setMaze(m);
        redraw();
    }

    public void setMaze(Maze maze, PlayerDisplayer p) {
        super.setMaze(maze);
        this.player = p;
        redraw();
    }

    public void setMaze(Maze maze) {
        super.setMaze(maze);
        redraw();
    }

    public void redraw() {
        if (maze != null) {
            try {
                Image Wall = new Image(new FileInputStream(ImageFileWall.get()));
                Image Walk = new Image(new FileInputStream(ImageFileWalk.get()));
//                Image Start = new Image(new FileInputStream(ImageFileStart.get()));
                Image Goal = new Image(new FileInputStream(ImageFileGoal.get()));

                graphicsContext2D.clearRect(0, 0, canvasWidth, canvasHeight); //Clean the Canvas

                //Draw maze
                for (int row = 0; row < maze.getRows(); row++) {
                    for (int column = 0; column < maze.getColumns(); column++) {
                        if (maze.getValue(row, column) == 1)
                            graphicsContext2D.drawImage(Wall, column * cellHeight, row * cellWidth, cellHeight, cellWidth); //Draw Wall
//                    else if (isStartPosition(row,column))
//                        graphicsContext2D.drawImage(Start,row*cellHeight,column*cellWidth,cellHeight,cellWidth);// Draw start position
                        else if (isGoalPosition(row, column))
                            graphicsContext2D.drawImage(Goal, column * cellHeight, row * cellWidth, cellHeight, cellWidth);// Draw Goal position
                        else
                            graphicsContext2D.drawImage(Walk, column * cellHeight, row * cellWidth, cellHeight, cellWidth);// Draw a all legal path walk for the user
                    }
                }
                if (player != null) {
                    Image playerImg = new Image(new FileInputStream(player.getImageFilePlayer()));
                    graphicsContext2D.drawImage(playerImg, player.getPlayerPositionColumn() * cellHeight, player.getPlayerPositionRow() * cellWidth, cellHeight, cellWidth);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //---CHECKERS---

    private boolean isStartPosition(int row, int column) {
        return row == maze.getStartPosition().getRowIndex() &&
                column == maze.getStartPosition().getColumnIndex();
    }

    private boolean isGoalPosition(int row, int column) {
        return row == maze.getGoalPosition().getRowIndex() &&
                column == maze.getGoalPosition().getColumnIndex();
    }


    //---GETTERS & SETTERS---

    public void setImageFileWall(String imageFileWall) {
        this.ImageFileWall.set(imageFileWall);
    }

    public void setImageFileWalk(String imageFileWalk) {
        this.ImageFileWalk.set(imageFileWalk);
    }

    public void setImageFileStart(String imageFileStart) {
        this.ImageFileStart.set(imageFileStart);
    }

    public void setImageFileGoal(String imageFileGoal) {
        this.ImageFileGoal.set(imageFileGoal);
    }

    public String getImageFileWall() {
        return this.ImageFileWall.get();
    }

    public String getImageFileWalk() {
        return this.ImageFileWalk.get();
    }

    public String getImageFileStart() {
        return this.ImageFileStart.get();
    }

    public String getImageFileGoal() {
        return this.ImageFileGoal.get();
    }


}
