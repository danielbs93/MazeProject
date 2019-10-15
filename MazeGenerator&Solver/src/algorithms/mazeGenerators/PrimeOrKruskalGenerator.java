package algorithms.mazeGenerators;

/**
 * Created by Daniel Ben Simon
 */

import java.util.*;

public class PrimeOrKruskalGenerator extends AMazeGenerator {

    private LinkedList<Edge> EdgeSet;
    private LinkedList<Set<Edge>> Buckets;
    private int nextFreeVectorIndex = 0;
    private Edge Start, Goal;

    @Override
    public Maze generate(int r, int c) {
        EdgeSet = new LinkedList<>();
        //Buckets = new Vector<>(10,1);
        Buckets = new LinkedList<Set<Edge>>();
        //for (Set<Edge> s : Buckets)
        //    s = new HashSet<>();
        iMaze = new Maze(r, c);
        iMaze.Put1();
        CreateEdges();
        //set start edge
        iMaze.generateStartPosition();
        initEdges(iMaze.getStartPosition());
        //set goal edge
        iMaze.generateGoalPosition();
        initEdges(iMaze.getGoalPosition());
        SetStartandGoalEdges();
//        nextFreeVectorIndex++; // for kruskal algorithm --> comment 'nextFreeVectorIndex = 1' in initEdges
//        Kruskal();
        Prime();
        return iMaze;
    }

    /**
     * Initializing the Start and Goal class parameters
     */
    private void SetStartandGoalEdges() {
        for (Edge e : Buckets.get(0))
            Start = e;
        for (Edge e : Buckets.get(1))
            Goal = e;
    }

    //----PRIME ALGORITHM----
    private void Prime() {
        while (!EdgeSet.isEmpty()) {
            for (int i = 0; i < Buckets.size(); i++) {
                Edge e = EdgeSet.get(new Random().nextInt(EdgeSet.size()));
                String state = whichEdgeExist(e);
                if (!state.equals("None")) { //nothing to do if "None" string option is returned//
                    if (state.equals("Both")){
                        EdgeSet.remove(e);
                    } else if (state.equals("Left and Right")) {// if the left vertex is belong to an edge in one of the sets and the right vertex is belong to an edge in other set
                        //merge sets
                        MergeSets(e);
                        ArrangeBucket();
                    } else if (state.equals("One Vertex")) {// if only on vertex is belong to an edge in one or the sets
                        //we add this edge to the current set
                        Buckets.get(i).add(e);
                    }
                }
            }
        }
        CreateMazeValues();
    }

    //----KRUSKAL ALGORITHM----
    private void Kruskal() {
        for (Edge e : EdgeSet) {
            for (int i = 0; i < Buckets.size(); i++) {
                String state = whichEdgeExist(e);
                if (!state.equals("Both")) { //nothing to do if "Both" string option is returned//
                    //e.OpenWall();
                    if (state.equals("None")) { // adding an edge to a new bucket
                        Buckets.add(Buckets.size() - 1, new HashSet<Edge>());
                        Buckets.get(Buckets.size() - 1).add(e);
                        nextFreeVectorIndex++;
                    } else if (state.equals("Left and Right")) {// if the left vertex is belong to an edge in one of the sets and the right vertex is belong to an edge in other set
                        //merge sets
                        MergeSets(e);
                        ArrangeBucket();
                    } else if (state.equals("One Vertex")) {// if only on vertex is belong to an edge in one or the sets
                        //we add this edge to the current set
                        Buckets.get(i).add(e);
                    }
                }else
                    EdgeSet.remove(e);
            }
        }
        CreateMazeValues();
    }

    /**
     * merging to sets into one and deleting the other one
     *
     * @param e
     */
    private void MergeSets(Edge e) {
        Set<Edge> setLeft = new HashSet<>();
        Set<Edge> setRight = new HashSet<>();
        for (Set<Edge> set : Buckets)
            for (Edge edge : set) {
                if (e.getLeft().equals(edge.getLeft()) || e.getLeft().equals(edge.getRight()))
                    setLeft = set;
                if (e.getRight().equals(edge.getRight()) || e.getRight().equals(edge.getLeft()))
                    setRight = set;
            }
        setLeft.addAll(setRight);
        Buckets.remove(setRight);
    }

    /**
     * After we have merged 2 sets we organize the Buckets
     */
    private void ArrangeBucket() {
        LinkedList<Set<Edge>> temp = new LinkedList<>();
        int nextfreeindex = 0;
        for (int i = 0; i < Buckets.size(); i++) {
            if (Buckets.get(i) != null && Buckets.get(i).size() != 0) {
                temp.add(nextfreeindex, new HashSet<Edge>());
                temp.set(nextfreeindex, Buckets.get(i));
                nextfreeindex++;
            }
        }
        Buckets = temp;
        this.nextFreeVectorIndex = nextfreeindex;
    }

    /**
     * @param e
     * @return string indicator both if left and right are in the same set
     * Ledt and Right if two of them exists in different sets
     * One Vertex if only one of then exist in a set
     */
    private String whichEdgeExist(Edge e) {
        boolean vertexLeft = false;
        boolean vertexRight = false;
        boolean both = false;
        for (Set<Edge> set : Buckets) {
            boolean left = false, right = false;
            for (Edge edge : set) {
                if (e.LeftSameBucket(edge)) {
                    vertexLeft = true;
                    left = true;
                }
                if (e.RightSameBucket(edge)) {
                    vertexRight = true;
                    right = true;
                }
            }
            if (left && right)
                both = true;
        }
        if (both)
            return "Both";
        else if (vertexLeft && vertexRight)
            return "Left and Right";
        else if (vertexRight || vertexLeft)
            return "One Vertex";
        else
            return "None";
    }

    /**
     * @param p - position to initialize Start and Goal edges
     */
    private void initEdges(Position p) {
        Position right = new Position(-1, -1);
        Edge result = new Edge(p, right);
        for (Edge e : EdgeSet) {
            if (e.OneVertexExist(result)) {
                e.OpenWall();
                e.setLeftValue();// value of 0
                e.setRightValue();
                Buckets.add(nextFreeVectorIndex, new HashSet<Edge>());
                Buckets.get(nextFreeVectorIndex).add(e);
                EdgeSet.remove(e);
                break;
            }
        }
        nextFreeVectorIndex = 1;
    }

    /**
     * Creates all legal edges between each Cell to its neighbors
     */
    private void CreateEdges() {
        Position[][] PositionArray = iMaze.PositionsArray();
        Edge e = null;
        for (Position[] arr : PositionArray) {
            for (Position p : arr) {
                //Upper Cell
                if (isValidEdge(p.row - 1, p.column)) {
                    e = new Edge(p, PositionArray[p.row - 1][p.column]);
                    if (!doesEdgeExistInSet(e, this.EdgeSet))
                        EdgeSet.add(e);
                }
                //Lower Cell
                if (isValidEdge(p.row + 1, p.column)) {
                    e = new Edge(p, PositionArray[p.row + 1][p.column]);
                    if (!doesEdgeExistInSet(e, this.EdgeSet))
                        EdgeSet.add(e);
                }
                //Left Cell
                if (isValidEdge(p.row, p.column - 1)) {
                    e = new Edge(p, PositionArray[p.row][p.column - 1]);
                    if (!doesEdgeExistInSet(e, this.EdgeSet))
                        EdgeSet.add(e);
                }
                //Right Cell
                if (isValidEdge(p.row, p.column + 1)) {
                    e = new Edge(p, PositionArray[p.row][p.column + 1]);
                    if (!doesEdgeExistInSet(e, this.EdgeSet))
                        EdgeSet.add(e);
                }
            }
        }
        Collections.shuffle(EdgeSet);
    }

    /**
     * @param e
     * @param set
     * @return true if an edge is exist in a set
     * 2 options {v1,v2} or {v2,v1} will be checked
     */
    private boolean doesEdgeExistInSet(Edge e, LinkedList<Edge> set) {
        if (set == null || set.size() == 0)
            return false;
        for (Edge edge : set) {
            if (e.LeftSameBucket(edge) && e.RightSameBucket(edge))
                return true;
        }
        return false;
    }

    /**
     * @param row
     * @param col
     * @return true if and only if the wanted vertex is legal and exist
     */
    private boolean isValidEdge(int row, int col) {
        if (row < 0 || col < 0)
            return false;
        if (row >= iMaze.getRows() || col >= iMaze.getColumns())
            return false;
        return true;
    }

    /**
     * Since we used kruskal algorithm to create the maze now we need to put 0 value to all sets we received from Kruskal()
     * The rule: we put 0 only to cells that have 2 or more open walls since we want to make sure there is a path that cross inside them
     */
    private void CreateMazeValues() {
        for (Set<Edge> set : Buckets) {
            int i = (set.size() - 4);
            for (Edge e : set) {
                int target = e.doesPathExist();
                if (target == 0) {// Both left&right vertexes as 2 or more open walls
                    e.setRightValue();
                    e.setLeftValue();
                } else if (target == 1)// only right vertex has 2 or more open walls
                    e.setRightValue();
                else// only left vertex has 2 or more open walls
                    e.setLeftValue();
            }
        }
    }

    public void printEdgesSet() {
        for (Edge e : this.EdgeSet) {
            System.out.println("{" + e.getLeft().row + "," + e.getLeft().column + "}--{" + e.getRight().row + "," + e.getRight().column + "}");
        }
    }
}
