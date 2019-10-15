package Server;

/**
 * Created by Daniel Ben Simon
 */

import algorithms.mazeGenerators.*;
import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    /**
     *
     * @param inFromClient InputStream to get details of the maze
     * @param outToClient Outputstream to return compressed maze
     */
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        int[] details;
        try {
            ObjectOutputStream toClient;
            ObjectInputStream fromClient;
                fromClient = new ObjectInputStream(inFromClient);
                toClient = new ObjectOutputStream(outToClient);
                toClient.flush();
                details = (int[])fromClient.readObject();
                Maze maze = generateMaze(details);
                toClient.writeObject(maze.toByteArray());
                toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param arr
     * @return compressed maze by a specific generator type according to configuration file
     */
    private Maze generateMaze(int[] arr) {
        Configurations c = null;
        try{ c = new Configurations(); }
        catch (Exception e){ e.getCause(); }
        String generatorType = c.getGeneratorType();
        if (generatorType.equals("Simple")) {
            SimpleMazeGenerator generator = new SimpleMazeGenerator();
            Maze maze = generator.generate(arr[0],arr[1]);
            return maze;
        }else if (generatorType.equals("Empty")) {
            EmptyMazeGenerator generator = new EmptyMazeGenerator();
            Maze maze = generator.generate(arr[0],arr[1]);
            return maze;
        }else if (generatorType.equals("MyMaze")) {
            MyMazeGenerator generator = new MyMazeGenerator();
            Maze maze = generator.generate(arr[0],arr[1]);
            return maze;
        }
        else
            return null;
    }


}
