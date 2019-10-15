package algorithms.search;

/**
 * Created by Daniel Ben Simon
 */

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch {

    public BestFirstSearch() {
        super();
        this.setName("Best First Search");
        m_Paths = new PriorityQueue<AState>(StateComparator);
    }

    /**
     * Setting Priority Queue for this algo
     * @param domain
     * @return Solution path
     */
    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null)
            return null;
        domain.setDiagonalMove(true);
        super.SetCondition(domain);
        return super.BFS(domain);
    }


}
