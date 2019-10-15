package algorithms.mazeGenerators;

/**
 * Created by Daniel Ben Simon
 */

import java.util.LinkedList;

public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     *
     * @param r - row
     * @param c - column
     * @return
     */

    @Override
    public Maze generate(int r, int c) {
        iMaze = new Maze(r, c);
        RandomMaze(iMaze);
        LinkedList<Position> solution = new LinkedList<>();
        iMaze.generateStartPosition();
        solution.add(iMaze.getStartPosition());
        iMaze.setValue(iMaze.getStartPosition().getRowIndex(),iMaze.getStartPosition().getColumnIndex(),0);
        iMaze.generateGoalPosition();
        Position next = getNextPosition(iMaze, solution, iMaze.getStartPosition());
        while (next != null && !next.equals(iMaze.getGoalPosition())) {
            solution.add(next);
            iMaze.setValue(next.getRowIndex(),next.getColumnIndex(),0);
            Position temp = next;
            next = getNextPosition(iMaze, solution, next);
        }
        solution.add(iMaze.getGoalPosition());
        iMaze.setValue(iMaze.getGoalPosition().getRowIndex(),iMaze.getGoalPosition().getColumnIndex(),0);


        return iMaze;
    }

    /**
     * generate random numbers in maze
     *
     * @param maze
     */
    private void RandomMaze(Maze maze) {
        for (int i = 0; i < maze.getRows(); i++)
            for (int j = 0; j < maze.getColumns(); j++) {
                if (Math.random() < 0.4) {
                    maze.setValue(i, j, 0);
                } else
                    maze.setValue(i, j, 1);
            }
    }

    /**
     * retrieve next possible positions
     * @param maze
     * @param Path
     * @param current
     * @return
     */
    private Position getNextPosition(Maze maze, LinkedList<Position> Path, Position current) {
        Position[] Possibilities = new Position[4];
        int Possib_Counter = -1;
        if (GoUp(maze, current) != null && Path.getFirst().getRowIndex() >= maze.getRows()/2) {
                Possib_Counter++;
                Possibilities[Possib_Counter] = GoUp(maze, current);
                return GoUp(maze,current);
        }
        if (GoRight(maze, current) != null && (Path.getFirst().getRowIndex() >= maze.getRows()/2
                || Path.getFirst().getColumnIndex() < maze.getColumns()/2)) {
                Possib_Counter++;
                Possibilities[Possib_Counter] = GoRight(maze, current);
                return GoRight(maze,current);
//            }
        }
        if (GoDown(maze, current) != null && !(Path.getFirst().getRowIndex() >= maze.getRows()/2)) {
                Possib_Counter++;
                Possibilities[Possib_Counter] = GoDown(maze, current);
                return GoDown(maze,current);
//            }
        }
        if (GoLeft(maze, current) != null && Path.getFirst().getColumnIndex() >= maze.getColumns()/2 ) {
                Possib_Counter++;
                Possibilities[Possib_Counter] = GoLeft(maze, current);
                return GoLeft(maze,current);
//            }
        }
        if (Possib_Counter == -1)// not found any point thus found the solution
            return null;
        if (Possib_Counter == 0)// only 1 point is legal
            return Possibilities[0];
        int resultIndex = (int)(Math.random()*Possib_Counter);
        return Possibilities[resultIndex];
    }

    private Position GoUp(Maze maze, Position P) {
        if (P == null || maze == null)
            return null;
        if (P.getRowIndex() - 1 > 0)
            return new Position(P.getRowIndex() - 1, P.getColumnIndex());
        return null;
    }

    private Position GoDown(Maze maze, Position P) {
        if (P == null || maze == null)
            return null;
        if (P.getRowIndex() + 1 < maze.getRows())
            return new Position(P.getRowIndex() + 1, P.getColumnIndex());
        return null;
    }

    private Position GoRight(Maze maze, Position P) {
        if (P == null || maze == null)
            return null;
        if (P.getColumnIndex() + 1 < maze.getColumns())
            return new Position(P.getRowIndex(), P.getColumnIndex() + 1);
        return null;
    }

    private Position GoLeft(Maze maze, Position P) {
        if (P == null || maze == null)
            return null;
        if (P.getColumnIndex() - 1 > 0)
            return new Position(P.getRowIndex(), P.getColumnIndex() - 1);
        return null;
    }

    public void print() {
        if (iMaze != null)
            iMaze.print();
    }

}
