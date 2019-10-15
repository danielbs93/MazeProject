package algorithms.search;

/**
 * Created by Daniel Ben Simon
 */

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.io.Serializable;

public class MazeState extends AState implements Serializable {

    private Position position;
    private boolean isDiagonal;

    /**
     *
     * @return true only if we can move diagonal from cell to cell
     * moving diagonal can be achieved only if there is a path (0 values) between two vertical cells
     */
    public boolean isDiagonal() {
        return isDiagonal;
    }

    //----CONSTRUCTORS----
    public  MazeState() {
        position = new Position();
        isDiagonal = false;
        this.Cost = Double.MAX_VALUE;
        this.TotalPathCost = Double.MAX_VALUE;
    }

    public MazeState(Position position, boolean diagonal) {
        this.position = position;
        isDiagonal = diagonal;
        this.m_StateName = position.toString();
        this.Cost = Double.MAX_VALUE;
        this.TotalPathCost = Double.MAX_VALUE;

    }

    public MazeState(AState m_ParentState, Position position, boolean isDiagonal) {
        super(m_ParentState);
        this.position = position;
        this.isDiagonal = isDiagonal;
        this.m_StateName = position.toString();
        this.Cost = Double.MAX_VALUE;
        this.TotalPathCost = Double.MAX_VALUE;
    }

    public void setDiagonal(boolean diagonal) {
        isDiagonal = diagonal;
    }

    @Override
    public String toString() {
        return position.toString();
    }

    @Override
    public void Print() {
        position.Print();
    }

    @Override
    public boolean isVisited() {
        return position.isVisited();
    }

    @Override
    public void setVisited(boolean visited) {
        position.setVisited(visited);
    }

    public void setPosition(Position p) {
        if (p != null)
            this.position = p;
    }

    public void setPosition(MazeState state) {
        if (state != null)
            this.position = state.position;
    }

    public Position getGoalPosition(Maze maze){
        return maze.getGoalPosition();
    }

    public Position getPosition() {
        return this.position;
    }

    public void setValue(int value) {
        this.position.setValue(value);
    }

    public int getValue() {
        return this.position.getValue();
    }

    @Override
    public boolean equals(Object state) {
        if (!(state instanceof MazeState) || state == null)
            return false;
        return this.m_StateName.equals(((MazeState)state).m_StateName);
    }

}
