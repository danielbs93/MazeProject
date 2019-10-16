package View;

import algorithms.mazeGenerators.Maze;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Maze3DDisplayer extends Displayer implements MazeDisplayer, EventHandler<ActionEvent> {


    private StringProperty someProperty;

    public String getProperty() {
        return someProperty.get();
    }

    public void setProperty(String someProperty) {
        this.someProperty.set(someProperty);
    }

    public void setMaze(Maze M3D) {
        super.setMaze(M3D);
    }

    private int[][][] maze;

    public void setDimentions(int[][][] maze){
        this.maze = maze;
        redraw();
    }

    public void redraw(){
        if (maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight/maze.length;
            double cellWidth = canvasWidth/maze[0].length;

            graphicsContext2D.clearRect(0,0,canvasWidth,canvasHeight); //Clean the Canvas
            graphicsContext2D.setFill(Color.BLACK); //Set color to the context

            //Draw maze
            for (int row = 0; row < maze.length; row++) {
                for (int column = 0; column < maze[row].length; column++) {
                    for (int height = 0; height < maze[row][column].length; height++)
                    if (maze[row][column][height] == 1){
                        graphicsContext2D.fillRect(row*cellHeight,column*cellWidth,cellHeight,cellWidth); //Draw Wall
                    }
                }
            }
        }
    }

    @Override
    public void handle(ActionEvent event) {

    }
}
