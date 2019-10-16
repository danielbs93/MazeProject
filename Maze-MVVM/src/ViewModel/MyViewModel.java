package ViewModel;

import Model.IModel;
import View.IView;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.Observable;
import java.util.Observer;


public class MyViewModel extends Observable implements Observer {

    private IModel model;

    private int playerRowIndex;
    private int playerColumnIndex;

    public StringProperty playerPositionRow = new SimpleStringProperty("1");
    public StringProperty playerPositionColumn = new SimpleStringProperty("1");

    public MyViewModel(IModel model) { this.model = model; }


    @Override
    public void update(Observable o, Object arg) {
        if (o==model){

            //if it's loaded maze
            if(arg != null){
                playerRowIndex = model.getMaze().getStartPosition().getRowIndex();
                playerPositionRow.set(playerRowIndex + "");
                playerColumnIndex = model.getMaze().getStartPosition().getColumnIndex();
                playerPositionColumn.set(playerColumnIndex + "");
            }
            else {//if it's new generated maze
                playerRowIndex = model.getPlayerRow();
                playerPositionRow.set(playerRowIndex + "");
                playerColumnIndex = model.getPlayerColumn();
                playerPositionColumn.set(playerColumnIndex + "");
            }
            setChanged();
            notifyObservers();
        }
    }


    public Maze getMaze(){return model.getMaze();}

    public void generate2DMaze(int row, int column){model.generate2DMaze(row,column);}

    public void generate3DMaze(int row, int column, int height){}

    public void solve2DMaze(){ model.solve2DMaze();}

    public void PlayerisMoving(KeyCode x){model.PlayerisMoving(x);}

    public int getPlayerRow(){return model.getPlayerRow();}

    public int getPlayerColumn(){return model.getPlayerColumn();}

    public Solution getSolution(){return model.getSolution();}

    public void LoadMaze(File file){model.LoadMaze(file);}

    public void SaveMaze(File file){model.SaveMaze(file);}

    public void Exit(){model.Stop();}

    public boolean isStartPosition(int r, int c){return model.isStartPosition(r,c);}

    public boolean isGoalPosition(int r, int c){return model.isGoalPosition(r,c);}

    public boolean isWallPosition(){return model.isWallPosition();}

}