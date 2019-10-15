package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Created by Daniel Ben Simon
 */

public class Cell implements Serializable {

    //-----Walls------
    private boolean UpWall = false;
    private boolean RightWall = false;
    private boolean LeftWall = false;
    private boolean DownWall = false;

    //---Getters & Setters---


    public boolean isUpWallOpen() {
        return UpWall;
    }

    protected void OpenUpWall(boolean upWall) {
        UpWall = upWall;
    }

    public boolean isRightWallOpen() {
        return RightWall;
    }

    protected void OpenRightWall(boolean rightWall) {
        RightWall = rightWall;
    }

    public boolean isLeftWallOpen() {
        return LeftWall;
    }

    protected void OpenLeftWall(boolean leftWall) {
        LeftWall = leftWall;
    }

    public boolean isDownWallOpen() {
        return DownWall;
    }

    protected void OpenDownWall(boolean downWall) {
        DownWall = downWall;
    }

}
