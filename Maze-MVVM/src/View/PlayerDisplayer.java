package View;

import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class PlayerDisplayer extends Displayer {

    private int PlayerPositionRow;
    private int PlayerPositionColumn;
    private StringProperty ImageFilePlayer = new SimpleStringProperty();

    //---INITIALIZING---

    public void setPlayer(int row, int column, Maze m) {
        super.setMaze(m);
        PlayerPositionRow = row;
        PlayerPositionColumn = column;
        //redraw();
    }


    @Override
    public void redraw() {
        if (maze != null) {
            try {
                if (this.maze.getValue(this.PlayerPositionRow, this.PlayerPositionColumn) != 1) {
                    Image player = new Image(new FileInputStream(ImageFilePlayer.get()));
                    //GraphicsContext gc = getGraphicsContext2D();
                    graphicsContext2D.clearRect(0, 0, canvasWidth, canvasHeight); //Clean the Canvas
                    //setSizes();
                    graphicsContext2D.drawImage(player, PlayerPositionRow * cellHeight, PlayerPositionColumn * cellWidth, cellHeight, cellWidth);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //---GETTERS & SETTERS---
//    private void setSizes(){
//        this.canvasHeight = getHeight();
//        this.canvasWidth = getWidth();
//        this.cellHeight = canvasHeight / this.maze.getRows();
//        this.cellWidth = canvasWidth / this.maze.getColumns();
//    }
    public int getPlayerPositionRow() {
        return PlayerPositionRow;
    }

    public int getPlayerPositionColumn() {
        return PlayerPositionColumn;
    }

    public String getImageFilePlayer() {
        return ImageFilePlayer.get();
    }

    public StringProperty imageFilePlayerProperty() {
        return ImageFilePlayer;
    }

    public void setPlayerPositionRow(int playerPositionRow) {
        PlayerPositionRow = playerPositionRow;
    }

    public void setPlayerPositionColumn(int playerPositionColumn) {
        PlayerPositionColumn = playerPositionColumn;
    }

    public void setImageFilePlayer(String imageFilePlayer) {
        this.ImageFilePlayer.set(imageFilePlayer);
    }
}
