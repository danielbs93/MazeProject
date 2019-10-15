package algorithms.search;

/**
 * Created by Daniel Ben Simon
 */

import java.util.LinkedList;

public interface ISearchable {
    public AState getStartState();
    public AState getGoalState();
    public LinkedList<AState> getPossibleNextStates(AState state);
    public boolean isDiagonalMove();
    public void setDiagonalMove(boolean diagonalMove);
}
