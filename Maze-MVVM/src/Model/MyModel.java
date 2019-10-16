package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import IO.MyCompressorOutputStream;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MyModel extends Observable implements IModel {
    //Maze
    private Maze maze;
    private Solution sol;
    private int PlayerRow, PlayerCol;

    //Server
    private Server GeneratingServer;
    private Server ProblemSolverServer;
    private ExecutorService ThreadPool;


    public MyModel() {
        GeneratingServer = new Server(5402, 1000, new ServerStrategyGenerateMaze());
        ProblemSolverServer = new Server(5403, 1000, new ServerStrategySolveSearchProblem());
        Configurations Config = null;

        try {
            Config = new Configurations();
        } catch (Exception var5) {
            var5.getCause();
        }
        ThreadPool = Executors.newFixedThreadPool(Integer.parseInt(Config.getNumOfThreads()));
//        ThreadPool = Executors.newCachedThreadPool();
        startServers();
    }

    //---Getters & Setters---
    public Maze getMaze() {
        return maze;
    }

    public Solution getSolution() {
        return sol;
    }

    public int getPlayerRow() {
        return PlayerRow;
    }

    public int getPlayerColumn() {
        return PlayerCol;
    }

    public void setMaze(Maze maze) {
//        if (this.maze == null)
            this.maze = new Maze(maze);
//        else
//            this.maze = maze;
    }

    public void setSolution(Solution sol) {
        this.sol = new Solution(sol);
    }

    public void setPlayerRow(int playerRow) {
        PlayerRow = playerRow;
    }

    public void setPlayerCol(int playerCol) {
        PlayerCol = playerCol;
    }

    //---SERVERS & COMMUNICATIONS---

    public void startServers() {
        GeneratingServer.start();
        ProblemSolverServer.start();
    }

    public void Stop() {
        GeneratingServer.stop();
        ProblemSolverServer.stop();
        ThreadPool.shutdown();
        Platform.exit();
        System.exit(0);
    }

    private void Generating2DMazeServerCommunication(int row, int col) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
                        ObjectInputStream fromServer = new ObjectInputStream(inputStream);
                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[row * col * 8]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze);
                        setMaze(maze);
                        PlayerRow = maze.getStartPosition().getRowIndex();
                        PlayerCol = maze.getStartPosition().getColumnIndex();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    private void ProblemSolver2DMazeServerCommunication() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        maze.getStartPosition().setRow(PlayerRow);
                        maze.getStartPosition().setColumn(PlayerCol);
                        toServer.flush();
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        setSolution(mazeSolution);//set solution
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    //---GENERATE---

    public void generate2DMaze(int rows, int columns) {
//        new Thread(() -> {
//            Generating2DMazeServerCommunication(rows, columns);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            setChanged();
//            notifyObservers();
//        }).start();

//        Generating2DMazeServerCommunication(rows, columns);
//        setChanged();
//        notifyObservers();
        this.ThreadPool.execute(() -> {
            Generating2DMazeServerCommunication(rows, columns);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers();
        });

    }

    public int[][][] generate3DMaze(int rows, int columns, int height) {
        return null;
    }


    //---SOLVER---

    public void solve2DMaze() {
        ThreadPool.execute(() -> {
            ProblemSolver2DMazeServerCommunication();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers();
        });
    }

    //---MOVING PLAYER PROPERTIES---

    /**
     * @param row
     * @param col
     * @return is a move is legal and not out of bounds
     */
    private boolean isLegalStep(int row, int col) {
        return (row < this.maze.getRows() && row >= 0 &&
                col < this.maze.getColumns() && col >= 0 &&
                this.maze.getValue(row, col) == 0);
    }

    public void PlayerisMoving(KeyCode move) {
        switch (move) {
            case UP:
            case NUMPAD8://UP
                if (isLegalStep(PlayerRow - 1, PlayerCol)) {
                    PlayerRow--;
                }else
                    isWall = true;
                break;
            case DOWN:
            case NUMPAD2://DOWN
                if (isLegalStep(PlayerRow + 1, PlayerCol)) {
                    PlayerRow++;
                }else
                    isWall = true;
                break;
            case RIGHT:
            case NUMPAD6://RIGHT
                if (isLegalStep(PlayerRow, PlayerCol + 1)) {
                    PlayerCol++;
                }else
                    isWall = true;
                break;
            case LEFT:
            case NUMPAD4://LEFT
                if (isLegalStep(PlayerRow, PlayerCol - 1)) {
                    PlayerCol--;
                }else
                    isWall = true;
                break;
            case NUMPAD9://UP+RIGHT
                if (isLegalStep(PlayerRow - 1, PlayerCol + 1)) {
                    PlayerRow--;
                    PlayerCol++;
                }else
                    isWall = true;
                break;
            case NUMPAD3://DOWN+RIGHT
                if (isLegalStep(PlayerRow + 1, PlayerCol + 1)) {
                    PlayerRow++;
                    PlayerCol++;
                }else
                    isWall = true;
                break;
            case NUMPAD1://DOWN+LEFT
                if (isLegalStep(PlayerRow + 1, PlayerCol - 1)) {
                    PlayerRow++;
                    PlayerCol--;
                }else
                    isWall = true;
                break;
            case NUMPAD7://UP+LEFT
                if (isLegalStep(PlayerRow - 1, PlayerCol - 1)) {
                    PlayerRow--;
                    PlayerCol--;
                }else
                    isWall = true;
                break;
        }
            setChanged();
            notifyObservers();
            isWall = false;
    }

    //---LOAD & SAVE---

    public void SaveMaze(File file) {
        try {
            FileOutputStream fileWriter = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileWriter);
            objectOutputStream.writeObject(maze);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void LoadMaze(File file) {
        try {
            FileInputStream fileReader = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileReader);
            Maze LoadedMaze = (Maze) objectInputStream.readObject();
            if (LoadedMaze != null) {
                setMaze(LoadedMaze);
                PlayerCol = maze.getStartPosition().getColumnIndex();
                PlayerRow = maze.getStartPosition().getRowIndex();
                setChanged();
            }
            objectInputStream.close();
            fileReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isStartPosition(int row, int col) {
        return row == this.PlayerRow &&
                col == this.PlayerCol;
    }

    @Override
    public boolean isGoalPosition(int row, int col) {
        return row == maze.getGoalPosition().getRowIndex() &&
                col == maze.getGoalPosition().getColumnIndex();
    }

    /**
     *
     * @return true if a move encountered a wall to start the desire music
     */
    @Override
    public boolean isWallPosition() {
        return isWall;
    }

    private boolean isWall = false;

}

