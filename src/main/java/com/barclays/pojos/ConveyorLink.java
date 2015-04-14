package com.barclays.pojos;

import java.util.Objects;

/**
 * Created on 11/4/15, 7:08 PM
 * ConveyorLink.java
 *
 * @author gshankar
 */
public final class ConveyorLink {

    private ConveyorNode from;
    private ConveyorNode to;
    private double cost;

    public ConveyorLink(ConveyorNode from, ConveyorNode to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public ConveyorNode getFrom() {
        return from;
    }

    public ConveyorNode getTo() {
        return to;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof ConveyorNode)) {
            return false;
        }

        ConveyorLink other = (ConveyorLink) obj;

        return (this.from.equals(other.from) && this.to.equals(other.to));
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Link [" + from.getNodeId().getValue() + "->" + to.getNodeId().getValue() + " : " + cost + "]";
    }
}
