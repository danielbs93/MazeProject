package algorithms.search;

/**
 * Created by Daniel Ben Simon
 */

import java.util.*;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{

    protected int NumOfNodes;
    protected Queue<AState> m_Paths;
    protected Map<AState ,AState> m_PreviousState;
    protected String Name;


    public ASearchingAlgorithm() {
        m_Paths = new LinkedList<>();
        m_PreviousState = new HashMap<>();
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return NumOfNodes;
    }


    @Override
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public abstract Solution solve(ISearchable domain);

    /**
     * initializing the algorithm with start base conditions
     * @param domain
     */
    public void SetCondition(ISearchable domain) {
        AState Start = domain.getStartState();
        m_Paths.add(Start);
        m_PreviousState.put(Start,null);
        Start.setCost(0);
        Start.setTotalPathCost(0);
        NumOfNodes =  1;
    }


    /**
     * generate path after assigning the MazeStates into our m_PreviousState
     * @param domain
     * @return Solution path
     */
    protected Solution generatePath(ISearchable domain ){
        AState goalState = domain.getGoalState();
        if(!m_PreviousState.containsKey(goalState))
            return new Solution(new ArrayList<AState>());
        AState temp = m_PreviousState.get(goalState);
        ArrayList<AState> sol = new ArrayList<AState>();
        sol.add(goalState);
        while( temp != null){
            sol.add(temp);
            temp = m_PreviousState.get(temp);
        }
        Collections.reverse(sol);
        return new Solution(sol);
    }

    /**
     *
     * @param Algo
     * @param domain
     * @return how much time it took to create the maze
     */
    public long SearchAlgorithmTimeMillis(ASearchingAlgorithm Algo, ISearchable domain){
        long start = System.currentTimeMillis();
        Solution solution = Algo.solve(domain);
        long end = System.currentTimeMillis();
        return (end-start);
    }

}
