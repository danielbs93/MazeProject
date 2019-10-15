package algorithms.search;

/**
 * Created by Daniel Ben Simon
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable {

    private ArrayList<AState> SolutionPath;

    public Solution(ArrayList<AState> solutionPath) {
        SolutionPath = new ArrayList<>();
        SolutionPath = solutionPath;
    }

    public Solution(Solution sol) {
        SolutionPath = new ArrayList<>();
        if (sol != null)
            SolutionPath = sol.SolutionPath;
    }

    public void setSolutionPath(ArrayList<AState> path) {this.SolutionPath = path;}

    public ArrayList<AState> getSolutionPath() {
        return SolutionPath;
    }

}
