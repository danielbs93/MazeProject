package algorithms.search;

/**
 * Created by Daniel Ben Simon
 */

import java.util.LinkedList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {

    protected AStateComparator StateComparator;

    public DepthFirstSearch() {
        super();
        this.setName("Depth First Search");
        StateComparator = new AStateComparator(null);
    }

    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null)
            return null;
        domain.setDiagonalMove(false);
        SetCondition(domain);
        return DFS(domain);
    }

    /**
     * Setting more conditions
     * @param domain
     */
    @Override
    public void SetCondition(ISearchable domain) {
        super.SetCondition(domain);
        StateComparator.setGoal(domain.getGoalState());
    }

    /**
     * Using DFS algorithm to solve this maze
     * @param domain
     * @return Solution path
     */
    protected Solution DFS(ISearchable domain) {
        ((SearchableMaze) domain).ResetBoolFields();
        Stack<AState> Stack_UnVisited = new Stack<>();
        LinkedList<AState> neighbours;
        AState Start = domain.getStartState();
        AState Goal = domain.getGoalState();
        Stack_UnVisited.add(Start);
        AState element;
        while (!Stack_UnVisited.isEmpty() && !m_PreviousState.containsKey(Goal)) {
            element = Stack_UnVisited.pop();
            if (!element.isVisited()) {
                neighbours = domain.getPossibleNextStates(element);
                for (AState cur : neighbours) {
                    if (cur.equals(Goal)) {
                        cur.setVisited(true);
                        m_PreviousState.put(Goal, element);
                        Goal.setVisited(true);
                        break;
                    }
                    if (!m_PreviousState.containsKey(cur)) {
                        Stack_UnVisited.push(cur);
                        m_PreviousState.put(cur, element);
                        NumOfNodes++;
                        if (cur.getParentState() == null)
                            cur.setParentState(element);
                    }
                }
                neighbours.removeAll(neighbours);
            }
            element.setVisited(true);
        }

        if (!Goal.isVisited())
            return null;
        else
            return generatePath(domain);
    }
}

//    private void BackTracePath(LinkedList<AState> solutins, AState Start) {
//        if (solutins.size() > 0) {
//            AState minState = solutins.remove();
//            while (solutins.size() > 0) {
//                AState current = solutins.remove();
//                if (current.getTotalPathCost() < minState.getTotalPathCost())
//                    minState = current;
//            }
//            ArrayList<AState> result = new ArrayList<>();
//            while (!minState.equals(Start)) {
//                result.add(0,minState);
//                minState = minState.getParentState();
//            }
//            this.sol.setSolutionPath(result);
//        }
//        ArrayList<AState> result = new ArrayList<>();
//        Stack<AState> temp = new Stack<>();
//        AState current = end;
//        temp.push(end);
//        while(!current.equals(start)) {
//            current = current.getParentState();
//            if (current == null)
//                break;
//            temp.push(current);
//        }
//        while (!temp.isEmpty()) {
//            result.add(temp.pop());
//        }
//        this.solution.setSolutionPath(result);
//        this.FoundSolution = true;






//    public Solution solve(ISearchable domain) {
//        Stack<AState> stack = new Stack<AState>();
//        ArrayList<AState> solution = new ArrayList<>();
//        LinkedList<AState> Options;
//        AState StartState = domain.getStartState();
//        AState EndState = domain.getGoalState();
//        StartState.setVisited(true);
//        stack.add(StartState);
//        while (!stack.isEmpty()){
//            AState current = stack.pop();
//            while (current.isVisited() && !current.equals(StartState))
//                current = stack.pop();
//            solution.add(current);
//            if (current.equals(EndState))
//                break;
//            Options = domain.getPossibleNextStates(current);
//            for (int i=0; i < Options.size(); i++){
//                AState temp = Options.get(i);
//                if (temp != null && !temp.isVisited()) {
//                   if (temp.equals(EndState)){
//                       EndState.setParentState(temp);
//                       stack.add(temp);
//                       break;
//                   }
//                    stack.add(temp);
//                }
//            }
//            current.setVisited(true);
//        }
//        AState lastState = stack.pop();
//        if (!lastState.equals(EndState))
//            return null;
//        while (lastState != null){
//            solution.add(lastState);
//            lastState = lastState.getParentState();
//        }
//        this.solution = new Solution(solution);
//        this.FoundSolution = true;
//        return new Solution(solution);
//    }
