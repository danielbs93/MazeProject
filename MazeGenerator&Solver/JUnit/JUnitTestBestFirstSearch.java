import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class JUnitTestBestFirstSearch {


        @Test
        public void solve() throws Exception {
            IMazeGenerator mg = new MyMazeGenerator();
            long f = mg.measureAlgorithmTimeMillis(1000,1000);
            Maze maze = mg.generate(1000, 1000);
            SearchableMaze searchableMaze = new SearchableMaze(maze);
            ASearchingAlgorithm searcher = new BestFirstSearch();
            Solution solution = searcher.solve(searchableMaze);
            System.out.println(String.format("'%s' algorithm - nodes evaluated:%s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
            ArrayList<AState> solutionPath = solution.getSolutionPath();
            if (f >= 60001)
                throw new Exception("time over!");
            if (solutionPath == null)
                throw new Exception("solution path null");
        }
    }


