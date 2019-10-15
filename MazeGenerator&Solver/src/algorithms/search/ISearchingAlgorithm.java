package algorithms.search;

/**
 * Created by Daniel Ben Simon
 */

public interface ISearchingAlgorithm {
    Solution solve (ISearchable domain);
    String getName();
    int getNumberOfNodesEvaluated();
    long SearchAlgorithmTimeMillis(ASearchingAlgorithm Algo, ISearchable domain);

}