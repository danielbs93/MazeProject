package Server;

/**
 * Created by Daniel Ben Simon
 */

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerStrategySolveSearchProblem implements IServerStrategy{

    private String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    private String allDirPath = tempDirectoryPath + "/Mazes&Solutions";

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        ObjectOutputStream toClient;
        ObjectInputStream fromClient;
        Solution sol = null;
        byte[] CurrentMaze;
        try {
                    toClient = new ObjectOutputStream(outToClient);
                    fromClient = new ObjectInputStream(inFromClient);
                    toClient.flush();
                    CurrentMaze = ((Maze)fromClient.readObject()).toByteArray();
                Maze maze = new Maze(CurrentMaze);
                //for searching maze with prefix file name comparision to create solution
                String prefixName_Col_Row = Integer.toString(maze.getRows()) + "_" + Integer.toString(maze.getColumns());
                File MazeDirectory = getDirectory(tempDirectoryPath);
                boolean isExist = false;
                if (MazeDirectory != null && MazeDirectory.listFiles().length != 0)
                    for (File ReadMazeFile : MazeDirectory.listFiles())
                    {
                                byte[] readMaze = Files.readAllBytes(ReadMazeFile.toPath());
                                    if (ReadMazeFile.getName().contains(prefixName_Col_Row))
                                        if (Arrays.equals(CurrentMaze, readMaze))
                                        {
                                            //searching for solution in solution folder and return solution
                                            String mazeSol = "SOL_" + ReadMazeFile.getName();
                                            sol = RetrieveSolution(mazeSol);
                                            isExist = true;
                                            break;
                                        }
                    }
                    if (!isExist) {
                        int index = saveMaze(prefixName_Col_Row, CurrentMaze);
                        String prefixMazeName = prefixName_Col_Row + "_" + index;
                        sol = Solve(CurrentMaze);
                        saveSolution(prefixMazeName, sol);
                    }
                toClient.writeObject(sol);
                toClient.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param CompMaze
     * @return Solution object
     */
    private Solution Solve(byte[] CompMaze){
        Configurations c = null;
        try{ c = new Configurations(); }
        catch (Exception e){ e.getCause(); }
        String searchAlgo = c.getSearchAlgorithm();
        SearchableMaze maze = new SearchableMaze(new Maze(CompMaze));
        if (searchAlgo.equals("Best")) {
            BestFirstSearch bestFirstSearch = new BestFirstSearch();
            return bestFirstSearch.solve(maze);
        }else if (searchAlgo.equals("DFS")) {
            DepthFirstSearch dfs = new DepthFirstSearch();
            return dfs.solve(maze);
        }else if (searchAlgo.equals("BFS")) {
            BreadthFirstSearch bfs = new BreadthFirstSearch();
            return bfs.solve(maze);
        }
        else
            return null;
    }

    /**
     *
     * @param SolName format: SOL_'row'_'col'_index
     * @return
     */
    private Solution RetrieveSolution(String SolName){
        Solution result = null;
        String myPath = allDirPath + "/" + SolName;// add + ".txt"
        byte[] byteSol;
        int [] intSol = null;
        try {
            MyDecompressorInputStream in = new MyDecompressorInputStream(new FileInputStream(myPath));
            byteSol = in.readBytes();
            MyDecompressorInputStream Decompress = new MyDecompressorInputStream(new FileInputStream(myPath));
            intSol = Decompress.toInt(byteSol);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<AState> solPath = new ArrayList<>();
        for (int i = 0; i+1 < intSol.length; i+=2) {
            AState Current = new MazeState();
                ((MazeState) Current).setPosition(new Position(intSol[i], intSol[i + 1]));
            solPath.add(Current);
        }
        return new Solution(solPath);
    }

    /**
     *
     * @param byName comparing by name in the format: 'row'_'col'_index
     * @param file will be the tmpdir to read from
     * @return false if not found
     */
//    private boolean isExistSolution(String prefixName, File dir) {
//        String prefixSolName = "SOL_" + prefixName;
//        for (File CurrFile: dir.listFiles()
//             ) {
//                    if (prefixSolName.equals(CurrFile.getName()))
//                        return true;
//        }
//        return false;
//    }

    /**
     *
     * @param prefixName format:'row'_'col'_index
     * @param sol
     *  this method will compress the arraylist<AStat> to byte[] and write it to a new file with the format as above
     */
    private void saveSolution(String prefixName, Solution sol) {
        int[] from_AL_int = new int[sol.getSolutionPath().size() * 2 + 1];//saves first the size of arrayList to retrieve solution in the right size
        from_AL_int[0] = sol.getSolutionPath().size();
        int index = 1;
        for (AState State: sol.getSolutionPath()
             ) {
            MazeState CurState = (MazeState) State;
            from_AL_int[index] = CurState.getPosition().getRowIndex();
            from_AL_int[index+1] = CurState.getPosition().getColumnIndex();
            index += 2;
        }
        String prefixSolName = "SOL_" + prefixName;// + ".txt";
        String myPath = allDirPath + "/" + prefixSolName;
        try {
            File path = new File(myPath);
            MyCompressorOutputStream out = new MyCompressorOutputStream(new FileOutputStream(path));
            out.flush();
            byte[] result = out.toByte(from_AL_int);
            out.write(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param prefixMazeName format: 'row'_'col'
     * saving maze with format : 'row'_'col'_index
     */
    private int saveMaze(String prefixMazeName, byte[] CompMaze){
        File tmpdir = new File(allDirPath);
        File[] allFiles = tmpdir.listFiles();
        ArrayList<File> Row_Col_Files = new ArrayList<>();
        for (int i = 0; i < allFiles.length; i++) {
            if (allFiles[i].getName().contains(prefixMazeName))
                Row_Col_Files.add(allFiles[i]);
        }


        int nextIndex = Row_Col_Files.size();
        String myPath = allDirPath +"\\" + prefixMazeName + "_" + nextIndex;// + ".txt";
        try {
            File path = new File(myPath);
            MyCompressorOutputStream out = new MyCompressorOutputStream(new FileOutputStream(path));
            out.flush();
            out.write(CompMaze);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextIndex;
    }

    /**
     *
     * @param path in format: 'java.io.tmpdir'
     * @return 'java.io.tmpdir'/Mazes&Solutions
     */

    private File getDirectory(String path) {
        String DirPath = path + "\\Mazes&Solutions";
        File dir = new File(DirPath);
        if (dir != null && dir.exists() && dir.isDirectory()) {
            return dir;
        }
        else {
            dir.mkdirs();
            return dir;
        }
    }
}
