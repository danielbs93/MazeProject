package algorithms.search;

/**
 * Created by Daniel Ben Simon
 */

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected AStateComparator StateComparator;

    public BreadthFirstSearch() {
        super();
        this.setName("Breadth First Search");
        StateComparator = new AStateComparator(null);
//        sol = new Solution();
    }

    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null)
            return null;
        domain.setDiagonalMove(false);
        SetCondition(domain);
        return BFS(domain);
    }

    /**
     * set more conditions
     * @param domain
     */
    @Override
    public void SetCondition(ISearchable domain) {
        super.SetCondition(domain);
        StateComparator.setGoal(domain.getGoalState());
    }

    /**
     * Using BFS algorithm to solve the maze
     * @param domain
     * @return Solution path
     */

    protected Solution BFS(ISearchable domain) {
        ((SearchableMaze)domain).ResetBoolFields();
        LinkedList<AState> Options;
        AState Start = domain.getStartState();
        AState Goal = domain.getGoalState();
        AState CurrentMove;
        while(!m_Paths.isEmpty() && !m_PreviousState.containsKey(Goal)) {
            CurrentMove = m_Paths.remove();
            if (!CurrentMove.isVisited()) {
                Options = domain.getPossibleNextStates(CurrentMove);
                for (AState cur : Options) {
                    if (cur.equals(Goal)) {
                        m_Paths.add(Goal);
                        m_PreviousState.put(Goal, CurrentMove);
                        Goal.setParentState(CurrentMove);
                        Goal.setVisited(true);
                        break;
                    }
                    if (!m_PreviousState.containsKey(cur) ) {
                        m_Paths.add(cur);
                        m_PreviousState.put(cur, CurrentMove);
                        NumOfNodes++;
                        if (cur.getParentState() == null)
                            cur.setParentState(CurrentMove);
                    }
                }
                Options.removeAll(Options);
            }
            CurrentMove.setVisited(true);

        }
        if (!Goal.isVisited())
            return null;
        else
            return generatePath(domain);
    }
}
