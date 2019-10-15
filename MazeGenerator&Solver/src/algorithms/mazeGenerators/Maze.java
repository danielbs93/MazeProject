package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Created by Daniel Ben Simon
 */

public class Maze implements Serializable {
    private int rows;
    private int columns;
    private Position[][] m_Maze;
    private Position Start,End;

    //----Constructor----

    public Maze(int rows, int columns) {
        if (rows > 0 && columns > 0) {
            this.rows = rows;
            this.columns = columns;
            this.m_Maze = new Position[rows][columns];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < columns; j++) {
                    m_Maze[i][j] = new Position(i, j);
                    m_Maze[i][j].setValue(0);
                }
            Start = new Position();
            End = new Position();
        }
    }

    public Maze(Maze curMaze) {
        this.rows = curMaze.getRows();
        this.columns = curMaze.getColumns();
        this.m_Maze = new Position[rows][columns];
        for (int i = 0; i < curMaze.rows; i++) {
            for (int j = 0; j <curMaze.columns ; j++) {
                m_Maze[i][j] = new Position(i, j);
                m_Maze[i][j].setValue(curMaze.getValue(i,j));
            }
        }
        Start = new Position();
        End = new Position();
        Start.set(curMaze.getStartPosition().getRowIndex(), curMaze.getStartPosition().getColumnIndex());
        End.set(curMaze.getGoalPosition().getRowIndex(), curMaze.getGoalPosition().getColumnIndex());
    }

    public Maze (byte[] array){
        int i = 0, row = 0, column = 0;
        row = getDecompressValue(i,array);
        this.setRows(row);
        if (row > 127)
            i = i + 2;
        else
            i++;
        column = getDecompressValue(i,array);
        this.setColumns(column);
        if (column > 127)
            i = i + 2;
        else
            i++;
        int x1 = getDecompressValue(i,array);
        if (x1 > 127)
            i = i + 2;
        else
            i++;
        int y1 = getDecompressValue(i,array);
        if (y1 > 127)
            i = i + 2;
        else
            i++;
        Position S = new Position(x1,y1);
        this.setStartPosition(S);
        int x2 = getDecompressValue(i,array);
        if (x2 > 127)
            i = i + 2;
        else
            i++;
        int y2 = getDecompressValue(i,array);
        if (y2 > 127)
            i = i + 2;
        else
            i++;
        Position E = new Position(x2,y2);
        this.setGoalPosition(E);
        int count = 0;
        int one = 0 , zero = getDecompressValue(i,array);
        if (zero > 0) {
            if (zero > 127)
                i = i + 2;
            else
                i++;
        }
        else{
            i++;
            one = getDecompressValue(i,array);
            if (one > 127)
                i = i + 2;
            else
                i++;
        }
        this.m_Maze = new Position[row][column];
        for (int k = 0; k < row; k++) {
            for (int j = 0; j < column; j++) {
                m_Maze[k][j] = new Position(k, j);
                if (zero > 0) {
                    m_Maze[k][j].setValue(0);
                    zero--;
                    if (zero == 0 && i < array.length) {
                        one = getDecompressValue(i, array);
                        if (one > 127)
                            i = i + 2;
                        else
                            i++;
                    }
                } else {
                    m_Maze[k][j].setValue(1);
                    one--;
                    if (one == 0 && i < array.length) {
                        zero = getDecompressValue(i, array);
                        if (zero > 127)
                            i = i + 2;
                        else
                            i++;
                    }
                }
            }
        }
    }

    private int getDecompressValue(int i, byte[] array){
        int result = 0;
        if (i + 1 < array.length && array[i+1] < 0)
            result = array[i] + (-100)*array[i+1];
        else
            result = array[i];
        return result;
    }

    //----Getters & Setters----

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getValue(int x, int y){
        return m_Maze[x][y].getValue();
    }

    public void setValue(int indexX, int indexY, int value){
        this.m_Maze[indexX][indexY].setValue(value);
    }

    public Position getStartPosition() {
        return new Position(Start);
    }

    public void setStartPosition(Position p) {
        this.Start = new Position(p);
    }

    public Position getGoalPosition() {
        return new Position(End);
    }

    public void setGoalPosition(Position p) {
        this.End = new Position(p);
    }

    public Position getPosition(int row, int column) {
        return m_Maze[row][column];
    }

    //----Generating Start and End positions----

    public void generateStartPosition() {
        int randX = (int) (Math.random() * rows);
        int randY = (int) (Math.random() * columns);
        double randCases = Math.random();
        if (randCases < 0.5) {
            Start.setRow(randX);
            m_Maze[randX][0] = Start;
        }
        else {
            Start.setColumn(randY);
            m_Maze[0][randY] = Start;
        }
    }

    public void generateGoalPosition() {
        if (Start.getRowIndex() == 0)
            if (Start.getColumnIndex() < columns / 2) {
                End.set(rows - 1, columns - 1);
                m_Maze[rows - 1][columns - 1] = End;
            }
            else {
                End.set(rows - 1, 0);
                m_Maze[rows - 1][0] = End;
            }
        if (Start.getColumnIndex() == 0)
            if (Start.getRowIndex() < rows / 2) {
                End.set(rows - 1, columns - 1);
                m_Maze[rows - 1][columns - 1] = End;
            }
            else {
                End.set(0, columns - 1);
                m_Maze[0][columns - 1] = End;
            }
    }

    /**
     * printing maze using colors on the console
     */
    public void printMaze(){
        for (int i = 0; i < m_Maze.length; i++) {
            for (int j = 0; j < m_Maze[i].length; j++) {
                if (i == Start.getRowIndex() && j == Start.getColumnIndex()) {//startPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                } else if (i == End.getRowIndex() && j == End.getColumnIndex()) {//goalPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                } else if (m_Maze[i][j].getValue() == 1) System.out.print(" " + "\u001B[45m" + " ");
                else System.out.print(" " + "\u001B[107m" + " ");
            }
            System.out.println(" " + "\u001B[107m");
        }
    }

    /**
     * initialize maze with 1's
     */

    public void Put1() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                m_Maze[i][j].setValue(1);
    }

    public void print() {
        if (m_Maze != null){
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (Start.getRowIndex() == i && Start.getColumnIndex() == j)
                        System.out.print("S, ");
                    else if (End.getRowIndex() == i && End.getColumnIndex() == j)
                        System.out.print("E, ");
                    else
                        System.out.print(getValue(i, j) + ", ");

                }
                System.out.println();
            }

        }
    }
    //----Deep Copy m_Maze Position[][] array----
    public Position[][] getPositionsArray(){
        Position[][] result = new Position[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.columns; j++) {
                result[i][j] = new Position(m_Maze[i][j]);
            }
        }
        return result;
    }

    public  Position[][] PositionsArray() {
        return m_Maze;
    }

    /**
     *
     * @param i index in the array
     * @param value we want to change to byte
     * @param array
     * @return
     * if value is greater than 127 , every 100's will transform to -1 such that
     * max value we will be at this format: [-128,127]
     * while the second cell is max rest
     */
    private int insertValue(int i, int value, byte[] array){
        if (value > 127) {
            array[i] = (byte) (value % 100);
            i+=1;
            array[i] = (byte) (value/ -100);
        }
        else
            array[i] = (byte) (value);
        i+=1;
        return i;
    }

    /**
     * compress the maze to array of byte
     */
    public byte[] toByteArray (){
        byte [] temp = new byte[getRows()*getColumns() + 50];
        int i = 0;
        i = insertValue(i,this.getRows(),temp); /* row */
        i = insertValue(i,this.getColumns(),temp); /* column */
        i = insertValue(i, this.getStartPosition().getRowIndex() , temp); /* start.x */
        i = insertValue(i, this.getStartPosition().getColumnIndex() , temp); /* start.y */
        i = insertValue(i, this.getGoalPosition().getRowIndex() , temp); /* goal.x */
        i = insertValue(i, this.getGoalPosition().getColumnIndex() , temp); /* goal.y */
        int count0 = 0,count1 = 0;
        boolean Zero = true,One = false;
        for (int r = 0; r < this.getRows(); r++)
            for (int c = 0; c < this.getColumns(); c++){
                if (Zero) {
                    if (this.m_Maze[r][c].getValue() == 0)
                        count0++;
                    else {
                        i = insertValue(i, count0, temp);
                        count0 = 0;
                        count1 = 1;
                        Zero = false;
                    }
                }
                else{
                    if (this.m_Maze[r][c].getValue() == 1)
                        count1++;
                    else {
                        i = insertValue(i, count1, temp);
                        count1 = 0;
                        count0 = 1;
                        Zero = true;
                    }
                }
            }
        if (count0 > 0)
            i = insertValue(i, count0, temp);
        else if (count1 > 0)
            i = insertValue(i, count1, temp);
        byte [] result = new byte[i];
        int j = 0;
        while(j < i){
            result[j] = temp[j];
            j++;
        }
        return result;
    }
}
