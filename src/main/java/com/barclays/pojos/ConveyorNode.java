package com.barclays.pojos;

import com.barclays.constants.Gate;

/**
 * Created on 11/4/15, 7:07 PM
 * ConveyorNode.java
 *
 * @author gshankar
 */
public class ConveyorNode implements Comparable<ConveyorNode>{

    private Gate nodeId;
    private String nodeName;
    private Double minDistance = Double.POSITIVE_INFINITY;
    private ConveyorNode previous;

    public ConveyorNode(Gate nodeId, String nodeName){
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

    public Gate getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public Double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Double minDistance) {
        this.minDistance = minDistance;
    }

    public ConveyorNode getPrevious() {
        return previous;
    }

    public void setPrevious(ConveyorNode previous) {
        this.previous = previous;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }

        if (obj == null || !(obj instanceof ConveyorNode)){
            return false;
        }

        ConveyorNode other = (ConveyorNode) obj;

        return (this.nodeId.getValue().equals(other.nodeId.getValue()));
    }

    @Override
    public int hashCode() {
        return nodeId.getValue().hashCode();
    }

    @Override
    public int compareTo(ConveyorNode other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
