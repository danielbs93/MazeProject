package algorithms.search;

/**
 * Created by Daniel Ben Simon
 */

import algorithms.mazeGenerators.Maze;
import java.util.LinkedList;

public class SearchableMaze implements ISearchable {

    private Maze maze;
    private boolean DiagonalMove;
    private MazeState[][] m_Maze;
    private MazeState StartState,GoalState;


    public SearchableMaze(Maze maze) {
        this.maze = maze;
        DiagonalMove = false;
        m_Maze = new MazeState[maze.getRows()][maze.getColumns()];
        CreateMazeStates();
        StartState = m_Maze[maze.getStartPosition().getRowIndex()][maze.getStartPosition().getColumnIndex()];
        GoalState = m_Maze[maze.getGoalPosition().getRowIndex()][maze.getGoalPosition().getColumnIndex()];
    }

    /**
     * initializing all MazeState with the value of each Position
     */
    private void CreateMazeStates() {
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                m_Maze[i][j] = new MazeState(null,maze.getPosition(i,j),false);
                maze.getPosition(i,j).setVisited(false);
            }
        }
    }

    /**
     * Diagonal move - if we use a different cost for diagonal move compared to a regular move
     * @return
     */
    public boolean isDiagonalMove() {
        return DiagonalMove;
    }

    public void setDiagonalMove(boolean diagonalMove) {
        DiagonalMove = diagonalMove;
    }

    /**
     *
     * @param state
     * @return list of possibles neighbours states given a diagonal move
     */
    @Override
    public LinkedList<AState> getPossibleNextStates(AState state) {
        LinkedList<AState> result = getPossibleNextStates(((MazeState)state));
        for (AState s: result) {
            MazeState current = ((MazeState)s);
            if (!DiagonalMove) {//if we dont use best fs algorithm
                if ( !current.equals(StartState)) {//sets the cost and the total cost for each legal neighbor deleted: !current.isDiagonal() &&
                    current.setTotalPathCost(state.getTotalPathCost() + 1);
                    current.setCost(current.getTotalPathCost());
                }
            }
                else {//we use diagonal moves
                if (!current.isDiagonal() && !current.equals(StartState))
                    current.setTotalPathCost(state.getTotalPathCost() + 1);
                else if (!current.equals(StartState))
                    current.setTotalPathCost(state.getTotalPathCost() + 1.5);
                current.setCost(current.getTotalPathCost());
            }
        }
        return result;
    }


    @Override
    public AState getGoalState() {
        return GoalState;
    }

    public AState getStartState() {
        return StartState;
    }

    /**
     *
     * @param state
     * @return list of all possibles neighbours states
     */
    public LinkedList<AState> getPossibleNextStates (MazeState state){
        LinkedList<AState> nextStates = new LinkedList<>();
        int row = state.getPosition().getRowIndex();
        int column = state.getPosition().getColumnIndex();
        int MaxRow = maze.getRows();
        int MaxColumn = maze.getColumns();

        if (row-1 >= 0 && column+1 < MaxColumn && (maze.getPosition(row-1,column+1).getValue() == 0 || maze.getPosition(row-1,column+1).equals(maze.getGoalPosition()) ))/**  Upper Right diagonal  **/
            if ((column+1 < MaxColumn && maze.getValue(row,column+1)==0) || (row-1 >= 0 && maze.getValue(row-1,column)==0)) {
                nextStates.add(m_Maze[row - 1][ column + 1]);
                if (!m_Maze[row - 1][ column + 1].isVisited())
                    m_Maze[row - 1][ column + 1].setDiagonal(true);
            }

        if (row+1 < MaxRow && column+1 < MaxColumn && (maze.getPosition(row+1,column+1).getValue() == 0 || maze.getPosition(row+1,column+1).equals(maze.getGoalPosition()))) /**  Down right diagonal  **/
            if ((column+1 < MaxColumn && maze.getValue(row,column+1)==0)||(row+1 < MaxRow && maze.getValue(row+1,column)==0)){
                nextStates.add(m_Maze[row + 1][ column + 1]);
                if (!m_Maze[row + 1][ column + 1].isVisited())
                    m_Maze[row + 1][ column + 1].setDiagonal(true);
            }

        if (row+1 < MaxRow && column-1 >= 0 && (maze.getPosition(row+1,column-1).getValue() == 0 || maze.getPosition(row+1,column-1).equals(maze.getGoalPosition()))) /**  Down left diagonal  **/
            if ((column-1 >= 0 && (maze.getPosition(row,column-1).getValue() == 0)||(row+1 < MaxRow && maze.getValue(row+1,column)==0))){
                nextStates.add(m_Maze[row + 1][ column - 1]);
                if (!m_Maze[row + 1][ column - 1].isVisited())
                    m_Maze[row + 1][ column - 1].setDiagonal(true);
            }

        if (row-1 >= 0 && column-1 >= 0 && (maze.getPosition(row-1,column-1).getValue() == 0  || maze.getPosition(row-1,column-1).equals(maze.getGoalPosition()))) /**  Upper left diagonal  **/
            if ((column-1 >= 0 && maze.getValue(row,column-1)==0) || (row-1 >= 0 && maze.getValue(row-1,column)==0)){
                nextStates.add(m_Maze[row - 1][ column - 1]);
                if (!m_Maze[row - 1][ column - 1].isVisited())
                    m_Maze[row - 1][ column - 1].setDiagonal(true);
            }

        if (row-1 >= 0 && (maze.getPosition(row-1,column).getValue() == 0 || maze.getPosition(row-1,column).equals(maze.getGoalPosition()))) { /**  Up  **/
            if (m_Maze[row - 1][column].isDiagonal())
                m_Maze[row - 1][column].setDiagonal(false);
            nextStates.add(m_Maze[row - 1][column]);
        }

        if (column+1 < MaxColumn && (maze.getPosition(row,column+1).getValue() == 0 || maze.getPosition(row,column+1).equals(maze.getGoalPosition())) ) {   /**  Right  **/
            if (m_Maze[row][column+1].isDiagonal())
                m_Maze[row][column+1].setDiagonal(false);
            nextStates.add(m_Maze[row][column + 1]);
        }

        if (row+1 < MaxRow && (maze.getPosition(row+1,column).getValue() == 0 || maze.getPosition(row+1,column).equals(maze.getGoalPosition()))) { /**  Down  **/
            if (m_Maze[row + 1][column].isDiagonal())
                m_Maze[row + 1][column].setDiagonal(false);
            nextStates.add(m_Maze[row + 1][column]);
        }

        if (column-1 >= 0 && (maze.getPosition(row,column-1).getValue() == 0  || maze.getPosition(row,column-1).equals(maze.getGoalPosition()))) { /**   Left  **/
            if(m_Maze[row][column - 1].isDiagonal())
                m_Maze[row][column - 1].setDiagonal(false);
            nextStates.add(m_Maze[row][column - 1]);
        }

        return nextStates;
    }

    public void print() {
        maze.print();
    }

    /**
     * Every time we use another algorithm to solve the maze we need to reset all boolean fields
     */
    public void ResetBoolFields(){
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                m_Maze[i][j].setDiagonal(false);
                m_Maze[i][j].setVisited(false);
            }
        }
    }

}
