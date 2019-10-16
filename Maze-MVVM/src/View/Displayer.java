package View;

import algorithms.mazeGenerators.Maze;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

import java.awt.*;

public abstract class Displayer extends Canvas {
    protected Maze maze;
    protected double canvasHeight;
    protected double canvasWidth;
    protected double cellHeight;
    protected double cellWidth;
    protected GraphicsContext graphicsContext2D;

    public void setMaze(Maze maze) {
        this.maze = maze;
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        cellHeight = canvasHeight/maze.getColumns();
        cellWidth = canvasWidth/maze.getRows();
        graphicsContext2D = getGraphicsContext2D();
    }

    public abstract void redraw();

//    public GraphicsContext getGraphicsContext2D(){
//        return getGraphicsContext2D();
//    }
}
