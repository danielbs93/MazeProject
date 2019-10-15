package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Created by Daniel Ben Simon
 */

public class Edge implements Serializable {
    private Position Left,Right;

    public Edge(Position left, Position right) {
        Left = left;
        Right = right;
    }

    public Position getLeft() {
        return Left;
    }

    public void setLeft(Position left) {
        Left = left;
    }

    public Position getRight() {
        return Right;
    }

    public void setRight(Position right) {
        Right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Edge))
            return false;
        return (Left.equals(((Edge) obj).Left) && Right.equals(((Edge) obj).Right)) || (Left.equals(((Edge) obj).Right) && Right.equals(((Edge) obj).Left));
    }

    /**
     *
     * @param e
     * @return true if one of the vertexes is in the same bucket
     */
    public boolean OneVertexExist(Edge e) {
        if (e == null )
            return false;
        return (Left.equals(e.Left) || Right.equals(e.Right) || Left.equals(e.Right) || Right.equals(e.Left));
    }

    /**
     *
     * @param e
     * @return true if Left vertex is in the same bucket
     */
    public boolean LeftSameBucket(Edge e) {
        if (e == null)
            return false;
        return (Left.equals(e.Left) || Left.equals(e.Right));
    }

    /**
     *
     * @param e
     * @return true if Right vertex is in the same bucket
     */
    public boolean RightSameBucket(Edge e) {
        if (e == null)
            return false;
        return (Right.equals(e.Right) || Right.equals(e.Left));
    }

    /**
     * open wall between cells in due to recognize this edge as a part of the maze path
     */
    public void OpenWall() {
        Left.OpenWall(Right);
        Right.OpenWall(Left);
    }

    public  void setLeftValue() {
        Left.setValue(0);
    }

    public  void setRightValue() {
        Right.setValue(0);
    }

    /**
     * Does the Cells have path between them according to their boolean value of each wall
     * @return
     * -1 = only left vertes has 2 or more open walls
     * 0 = both have 2 or more open walls
     * 1 = only right vertex has 2 or more open walls
     * we put 0 value only to those who have 2 or more open walls
     */
    public int doesPathExist() {
        int counter = 0;
        boolean left = false, right = false;
        if (Left.TworMoreOpenWalls())
            left = true;
        if (Right.TworMoreOpenWalls())
            right = true;
        if (left && right)
            return 0;
        else if (left)
            return -1;
        else
            return 1;
    }

}
