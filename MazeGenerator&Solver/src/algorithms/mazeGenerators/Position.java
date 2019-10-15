package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Created by Daniel Ben Simon
 */

public class Position extends Cell implements Serializable {
    protected int row;
    protected int column;
    boolean isVisited;
    protected int Value;

    //----Constructors----

    public Position(){
        row = 0; column = 0; isVisited = false;
    }

    public Position(int iRow, int iColumn) {
        row = iRow;
        column = iColumn;
    }

    public Position(int iRow, int iColumn, int value) {
        row = iRow;
        column = iColumn;
        Value = value;
    }

    public Position(Position p){
        this.row = p.row;
        this.column = p.column;
        this.isVisited = p.isVisited;
        Value = p.Value;
    }

    //----Getters & Setters----

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public int getRowIndex() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumnIndex() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void set(int x, int y) {
        this.column = y;
        this.row = x;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    @Override
    public String toString() {
        return ("{" + row + "," + column + "}");
    }

    public void Print(){
        System.out.println("{" + row + "," + column + "}");
    }

    public boolean equals(Position p) {
        return (row == p.row && column == p.column);
    }

    public void OpenWall(Position p){
        if (this.row - 1 == p.row && this.column == p.column)
            OpenUpWall(true);
        if (this.row + 1 == p.row && this.column == p.column)
            OpenDownWall(true);
        if (this.row == p.row && this.column + 1 == p.column)
            OpenRightWall(true);
        if (this.row == p.row && this.column - 1 == p.column)
            OpenLeftWall(true);
    }

    /**
     *
     * @return true if 2 or more walls open to know if there is a path crossing through the cell
     */

    public boolean TworMoreOpenWalls() {
        if (super.isDownWallOpen())
            if (super.isLeftWallOpen() || super.isRightWallOpen() || super.isUpWallOpen())
                return true;
            else if (super.isUpWallOpen())
            if (super.isLeftWallOpen() || super.isRightWallOpen() || super.isDownWallOpen())
                return true;
            else if (super.isLeftWallOpen())
             if (super.isDownWallOpen() || super.isRightWallOpen() || super.isUpWallOpen())
                return true;
             else if (super.isRightWallOpen())
            if (super.isLeftWallOpen() || super.isDownWallOpen() || super.isUpWallOpen())
                return true;
            return  false;
    }
}


