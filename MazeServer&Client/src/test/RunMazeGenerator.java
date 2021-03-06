package test;

import algorithms.mazeGenerators.*;

/**
 * Created by Daniel and Eran
 */
public class RunMazeGenerator {
    public static void main(String[] args) {
        //testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());
    }

    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
        // prints the time it takes the algorithm to run
        System.out.println(String.format("Maze generation time(ms): %s", mazeGenerator.measureAlgorithmTimeMillis(5000/*rows*/,5000/*columns*/)));
        // generate another maze
        Maze maze = mazeGenerator.generate(50/*rows*/, 50/*columns*/);

        // prints the maze
        maze.print();
        byte [] b = maze.toByteArray();
        Maze m2 = new Maze(b);
        System.out.println("------------------ decompress maze : ------------------");
        m2.print();

        // get the maze entrance
        Position startPosition = maze.getStartPosition();


        // print the position
        System.out.println(String.format("Start Position: %s", startPosition)); // format "{row,column}"

        // prints the maze exit position
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));

    }
}