package algorithms.mazeGenerators;

/**
 * Created by Daniel Ben Simon
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator {
    /**
     * generating maze with positions
     * @param r - row
     * @param c - column
     * @return
     */
    @Override
    public Maze generate(int r, int c) {
        Stack<Position> m_Stack = new Stack<>();
        iMaze = new Maze(r,c);
        iMaze.Put1();
        iMaze.generateStartPosition();
        iMaze.generateGoalPosition();
        Position[][] p_Maze = getPositionsMaze(iMaze);
        dfs(m_Stack,p_Maze);
        return iMaze;
    }

    /**
     * using dfs algorithm to build a maze with at least one solution path
     * @param stack
     * @param maze
     */
    private void dfs(Stack<Position> stack, Position[][] maze) {
        Position Current = iMaze.getStartPosition();
        Position End = iMaze.getGoalPosition();
        End.setValue(0);
        setTrueOrFalse(maze,Current,true);
        Position next = getUnvisitedNeighbour(maze, Current);
        stack.push(Current);
        Current = next;
        while(!stack.empty()) {
            if (moreVisitedNeighboors(maze,Current) && !next.equals(End)) {
            next = getUnvisitedNeighbour(maze, Current);
                if (next != null) {
                    stack.push(Current);
                    set0toMaze(Current);
                    Current = next;
                }
                    setTrueOrFalse(maze,Current,true);
            } else if (!stack.isEmpty()) {
                try {
                    Current = stack.pop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * generate maze from Position[][] type
     * @param maze
     * @return
     */
    private Position[][] getPositionsMaze(Maze maze) {
        if (maze == null)
            return null;
        Position[][] result = new Position[maze.getRows()][maze.getColumns()];
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++)
                result[i][j] = new Position(i,j);
        }
        return result;
    }

    /**
     * true if we visited at the given position
     * @param maze
     * @param p
     * @param flag
     */
    private void setTrueOrFalse(Position[][] maze, Position p, boolean flag) {
        maze[p.getRowIndex()][p.getColumnIndex()].setVisited(flag);
    }

    /**
     * set value 0 to a particular position in the original maze (iMaze)
     * @param p
     */
    private void set0toMaze(Position p) {
        iMaze.setValue(p.getRowIndex(),p.getColumnIndex(),0);
    }

    /**
     * if we can visit either of neighboors of the specific position return true
     * @param maze
     * @param p
     * @return
     */
    private boolean moreVisitedNeighboors(Position[][] maze, Position p) {
        if (maze == null || p == null)
            return false;
        return !(isUpVisited(maze,p) && isDownVisited(maze,p)
                && isLeftVisited(maze,p) && isRightVisited(maze,p));
    }

    /**
     * return a random Position from all possible postions we can go ahead
     * @param maze
     * @param current
     * @return
     */
    private Position getUnvisitedNeighbour(Position[][] maze, Position current) {
        if (maze == null || current == null)
            return null;
        List<Position> Options = new ArrayList<>();
        Position result = new Position();
        int Option_Counter = 0;
        if (!isUpVisited(maze,current)) {
            Options.add(new Position(current.getRowIndex() - 1,current.getColumnIndex()));
            Option_Counter++;
        }
        if (!isDownVisited(maze,current)) {
            Options.add(new Position(current.getRowIndex() + 1,current.getColumnIndex()));
            Option_Counter++;
        }
        if (!isRightVisited(maze,current)) {
            Options.add(new Position(current.getRowIndex(),current.getColumnIndex() + 1));
            Option_Counter++;
        }
        if (!isLeftVisited(maze,current)) {
            Options.add(new Position(current.getRowIndex(),current.getColumnIndex() - 1));
            Option_Counter++;
        }
        if (Options.isEmpty())
            return null;
        Random rand = new Random();
        int randIndex = rand.nextInt(Option_Counter);
        return Options.get(randIndex);

    }

    private boolean isUpVisited(Position[][] maze, Position current) {
        if ((current.getRowIndex() - 1) >= 0)
            if (maze[current.getRowIndex() - 1][current.getColumnIndex()].isVisited() == false)
                return false;
            return true;
    }

    private boolean isDownVisited(Position[][] maze, Position current) {
        if ((current.getRowIndex() + 1) < maze.length)
            if (maze[current.getRowIndex() + 1][current.getColumnIndex()].isVisited() == false)
                return false;
            return true;
    }

    private boolean isRightVisited(Position[][] maze, Position current) {
        if ((current.getColumnIndex() + 1) < maze[0].length)
            if (maze[current.getRowIndex()][current.getColumnIndex() + 1].isVisited() == false)
                return false;
            return true;
    }

    private boolean isLeftVisited(Position[][] maze, Position current) {
        if ((current.getColumnIndex() - 1) >= 0)
            if (maze[current.getRowIndex()][current.getColumnIndex() - 1].isVisited() == false)
                return false;
            return true;
    }

    /**
     * checking if all positions were visited
     * @param maze
     * @return
     */
    private boolean isAllVisited(Position[][] maze) {
        if (maze == null)
            return false;
        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[0].length; j++)
                if (maze[i][j].isVisited == false)
                    return false;
        return true;
    }

}
