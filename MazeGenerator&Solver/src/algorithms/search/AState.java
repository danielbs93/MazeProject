package algorithms.search;

import java.io.Serializable;

/**
 * Created by Daniel Ben Simon
 */

public abstract class AState implements Serializable {

    //----DESCRIPTORS FOR ASTAT----

    protected String m_StateName;
    protected AState m_ParentState;
    protected double TotalPathCost;
    protected double Cost;

    public AState(){}

    public AState(AState m_ParentState) {
        this.m_ParentState = m_ParentState;
    }

    public abstract void Print();

    public String getName() {
        return m_StateName;
    }

    public abstract boolean isVisited();

    public abstract void setVisited(boolean visited);

    public void setStatName(String m_StatName) {
        this.m_StateName = m_StatName;
    }

    public AState getParentState() {
        return m_ParentState;
    }

    public void setParentState(AState m_ParentState) {
        this.m_ParentState = m_ParentState;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double Cost) {
        this.Cost = Cost;
    }

    public double getTotalPathCost() {
        return TotalPathCost;
    }

    public void setTotalPathCost(double totalPathCost) {
        TotalPathCost = totalPathCost;
    }

    public abstract boolean equals(Object state);

}
