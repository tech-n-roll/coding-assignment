package com.barclays.input;

import com.barclays.constants.Gate;
import com.barclays.pojos.Bag;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created on 12/4/15, 4:00 PM
 * BagHandler.java
 *
 * @author gshankar
 */
public class BagHandler implements InputHandler {

    private Map<String, Bag> bagIdToBagMap;

    @Override
    public void process() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.txt").getFile());

        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
            boolean startBagSection = false;
            boolean endBagSection = false;

            while (scanner.hasNextLine() && !endBagSection) {
                String line = scanner.nextLine();

                if (line.trim().equals("")){
                    continue;
                }

                if (line.startsWith("# Section:")) { //start of a new section
                    if (!line.endsWith("Bags")) { //skip
                        startBagSection = false;
                        continue;
                    } else if (line.endsWith("Bags")) {
                        startBagSection = true;
                        bagIdToBagMap = new LinkedHashMap<>();
                        continue;
                    } else if (startBagSection && !line.endsWith("Bags")) {
                        endBagSection = true;
                    }
                }

                if (startBagSection && !endBagSection) {
                    //Format: <bag_number> <entry_point> <flight_id>
                    String tokens[] = line.split(" ");
                    if (tokens.length != 3) {
                        throw new IOException("BAD INPUT FORMAT...");
                    }
                    String bagId = tokens[0];
                    Gate entryGate = Gate.getGate(tokens[1]);
                    String flightId = tokens[2];


                    if (entryGate == null) {
                        throw new IOException("INVALID ENTRY GATE FOUND...");
                    }

                    bagIdToBagMap.put(bagId, new Bag(bagId, entryGate, flightId));
                }

            }
        }finally {
            scanner.close();
        }
    }

    public Map<String, Bag> getBagIdToBagMap() {
        return bagIdToBagMap;
    }

    public void setBagIdToBagMap(Map<String, Bag> bagIdToBagMap) {
        this.bagIdToBagMap = bagIdToBagMap;
    }
}
