package algorithms.search;

/**
 * Created by Daniel Ben Simon
 */

import java.util.Comparator;

public class AStateComparator implements Comparator<AState> {

    protected AState Goal;

    public AStateComparator(AState goal) {
        Goal = goal;
    }

    public AState getGoal() {
        return Goal;
    }

    public void setGoal(AState goal) {
        Goal = goal;
    }

    /**
     *
     * @param o1
     * @param o2
     * @return THE SHORTEST COST (DISTANCE) BETWEEN O1 AND O2
     */
    @Override
    public int compare(AState o1, AState o2) {
        if(o1 instanceof MazeState && o2 instanceof MazeState && Goal instanceof MazeState){
            return Double.compare(o1.getCost(),o2.getCost());
        }
        return Integer.MAX_VALUE;    }
}
