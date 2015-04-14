package com.barclays.pojos;

import com.barclays.constants.Gate;

/**
 * Created on 11/4/15, 7:25 PM
 * Bag.java
 *
 * @author gshankar
 */
public class Bag {
    private String id;
    private Gate entryPoint;
    private String flightId;

    public Bag(){

    }

    public Bag(String bagId, Gate entryPoint, String flightId){
        this.id = bagId;
        this.entryPoint = entryPoint;
        this.flightId = flightId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Gate getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(Gate entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "id='" + id + '\'' +
                ", entryPoint=" + entryPoint +
                ", flightId='" + flightId + '\'' +
                '}';
    }
}
