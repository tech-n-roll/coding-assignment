package com.barclays.input;

import com.barclays.constants.Gate;
import com.barclays.pojos.FlightDeparture;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created on 12/4/15, 4:00 PM
 * FlightDepartureHandler.java
 *
 * @author gshankar
 */
public class FlightDepartureHandler implements InputHandler {

    Map<String, FlightDeparture> flightIdToDepartureMap;

    @Override
    public void process() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.txt").getFile());

        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
            boolean startDepartureSection = false;
            boolean endDepartureSection = false;

            while (scanner.hasNextLine() && !endDepartureSection) {
                String line = scanner.nextLine();

                if (line.trim().equals("")){
                    continue;
                }

                if (line.startsWith("# Section:")) { //start of a new section
                    if (!line.endsWith("Departures")) { //skip
                        startDepartureSection = false;
                        continue;
                    } else if (line.endsWith("Departures")) {
                        startDepartureSection = true;
                        flightIdToDepartureMap = new HashMap<>();
                        continue;
                    } else if (startDepartureSection && !line.endsWith("Departures")) {
                        endDepartureSection = true;
                    }
                }

                if (startDepartureSection && !endDepartureSection) {
                    //Format: <flight_id> <flight_gate> <destination> <flight_time>
                    String tokens[] = line.split(" ");
                    if (tokens.length != 4) {
                        throw new IOException("BAD INPUT FORMAT...");
                    }
                    String flightId = tokens[0];
                    String flightGate = tokens[1];
                    String destination = tokens[2];
                    String departureTime = tokens[3];

                    Gate departureGate = Gate.getGate(flightGate);

                    if (departureGate == null) {
                        throw new IOException("INVALID GATE FOUND...");
                    }

                    flightIdToDepartureMap.put(flightId, new FlightDeparture(flightId, departureGate, destination, departureTime));
                }

            }
        }finally {
            scanner.close();
        }
    }

    public Map<String, FlightDeparture> getFlightIdToDepartureMap() {
        return flightIdToDepartureMap;
    }

    public void setFlightIdToDepartureMap(Map<String, FlightDeparture> flightIdToDepartureMap) {
        this.flightIdToDepartureMap = flightIdToDepartureMap;
    }
}
