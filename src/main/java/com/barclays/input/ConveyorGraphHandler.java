package com.barclays.input;

import com.barclays.business.ConveyorGraph;
import com.barclays.constants.Gate;
import com.barclays.pojos.ConveyorNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created on 12/4/15, 3:59 PM
 * ConveyorGraphHandler.java
 *
 * @author gshankar
 */
public class ConveyorGraphHandler implements InputHandler {

    private ConveyorGraph conveyorGraph;

    private Map<Gate, ConveyorNode> gateNodeMap = new HashMap<>();

    @Override
    public void process() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.txt").getFile());

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            boolean startGraphSection = false;
            boolean endGraphSection = false;

            while (scanner.hasNextLine() && !endGraphSection) {
                String line = scanner.nextLine();

                if (line.trim().equals("")){
                    continue;
                }

                if (line.startsWith("# Section:")) { //start of a new section
                    if (!line.endsWith("Conveyor System")) { //skip
                        startGraphSection = false;
                        continue;
                    } else if (line.endsWith("Conveyor System")) {
                        startGraphSection = true;
                        conveyorGraph = new ConveyorGraph();
                        continue;
                    } else if (startGraphSection && !line.endsWith("Conveyor System")) {
                        endGraphSection = true;
                    }
                }

                if (startGraphSection && !endGraphSection) {
                    //format :: <Node 1> <Node 2> <travel_time>
                    String tokens[] = line.split(" ");
                    if (tokens.length != 3) {
                        throw new IOException("BAD INPUT FORMAT...");
                    }
                    String from = tokens[0];
                    String to = tokens[1];
                    int cost = Integer.parseInt(tokens[2]);

                    Gate fromGate = Gate.getGate(from);
                    Gate toGate = Gate.getGate(to);

                    if (fromGate == null || toGate == null) {
                        throw new IOException("INVALID GATE FOUND...");
                    }

                    //add the bi-directional link in the barclays
                    conveyorGraph.addLink(createNode(fromGate, gateNodeMap), createNode(toGate, gateNodeMap), cost);
                    conveyorGraph.addLink(createNode(toGate, gateNodeMap), createNode(fromGate, gateNodeMap), cost);
                }

            }
        } finally {
            scanner.close();
        }
    }

    public ConveyorGraph getConveyorGraph() {
        return conveyorGraph;
    }

    public void setConveyorGraph(ConveyorGraph conveyorGraph) {
        this.conveyorGraph = conveyorGraph;
    }

    private ConveyorNode createNode(Gate gate, Map<Gate, ConveyorNode> nodeMap) {
        if (nodeMap.containsKey(gate)) {
            return nodeMap.get(gate);
        }
        ConveyorNode conveyorNode = new ConveyorNode(gate, gate.getValue());
        nodeMap.put(gate, conveyorNode);
        return conveyorNode;
    }
}
