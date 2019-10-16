package algorithms.search;

/**
 * Created by Daniel Ben Simon
 * Best search algorithm builds a path with diagonals moves only if there is a vertical&horizontal path to that cell. 
 * For example:  0,0,1
 *               1,0,0  --> cell[0,0] to cell [1,1] legal diagonal move
 *               0,1,0  --> cell[1,1] to cell [0,2] illegal diagonal move
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
