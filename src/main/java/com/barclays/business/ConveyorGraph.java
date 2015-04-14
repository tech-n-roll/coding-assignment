package com.barclays.business;

import com.barclays.pojos.ConveyorLink;
import com.barclays.pojos.ConveyorNode;

import java.util.*;

/**
 * Created on 11/4/15, 9:34 PM
 * ConveyorGraph.java
 *
 * @author gshankar
 */
public class ConveyorGraph implements Graph<ConveyorNode, ConveyorLink> {

    //adjacency list representation of graph
    private Map<ConveyorNode, List<ConveyorLink>> neighbors = new HashMap<>();

    private Set<ConveyorNode> nodes = new HashSet<>();

    private Set<ConveyorLink> links = new HashSet<>();

    /**
     * Add a node to the graph.
     *
     * @param node
     */
    public void addNode(ConveyorNode node) {
        if (!neighbors.containsKey(node)) {
            neighbors.put(node, new ArrayList<ConveyorLink>());
            nodes.add(node);
        }
    }

    /**
     * Check if link exists between two nodes.
     *
     * @param from
     * @param to
     * @return
     */
    public boolean isLink(ConveyorNode from, ConveyorNode to) {
        List<ConveyorLink> links = neighbors.get(from);
        if (links != null && !links.isEmpty()) {
            for (ConveyorLink conveyorLink : links) {
                if (conveyorLink.getTo().equals(to)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Add a link to the Graph.
     *
     * @param from
     * @param to
     * @param cost
     */
    public void addLink(ConveyorNode from, ConveyorNode to, double cost) {
        addNode(from);
        addNode(to);

        if (!isLink(from, to)) {
            ConveyorLink link = new ConveyorLink(from, to, cost);
            neighbors.get(from).add(link);
            links.add(link);
        }
    }

    @Override
    public ConveyorLink getLink(ConveyorNode source, ConveyorNode target) {
        List<ConveyorLink> links = neighbors.get(source);
        for (ConveyorLink link : links) {
            if (link.getTo().equals(target)) {
                return link;
            }
        }
        return null;
    }


    @Override
    public Iterable<ConveyorNode> getNodes() {
        return nodes;
    }

    @Override
    public int getOrder() {
        return neighbors.size();
    }

    @Override
    public Iterable<ConveyorLink> getLinks() {
        return links;
    }

    @Override
    public int getSize() {
        return links.size();
    }

    @Override
    public boolean containsNode(ConveyorNode conveyorNode) {
        return neighbors.containsKey(conveyorNode);
    }

    @Override
    public boolean containsLink(ConveyorLink conveyorLink) {
        return links.contains(conveyorLink);
    }

    /**
     * Dijkstra's shortest path implementation
     * http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     *
     * @param source
     * @param target
     * @return
     */
    public List<ConveyorNode> findShortestPath(ConveyorNode source, ConveyorNode target) {
        List<ConveyorNode> shortestPath = new ArrayList<>();

        source.setMinDistance(0D);

        PriorityQueue<ConveyorNode> vertexQueue = new PriorityQueue<>();

        for (ConveyorNode vertex : nodes) {
            if (!vertex.equals(source)) {
                vertex.setMinDistance(Double.POSITIVE_INFINITY);
                vertex.setPrevious(null);
            } else {
                vertex = source;
            }
            vertexQueue.add(vertex);
        }

        while (!vertexQueue.isEmpty()) {
            ConveyorNode u = vertexQueue.poll();

            if (u.equals(target)) {
                while (u.getPrevious() != null) {
                    shortestPath.add(u);
                    u = u.getPrevious();
                }
                break;
            }

            vertexQueue.remove(u);

            List<ConveyorLink> edges = neighbors.get(u);

            for (ConveyorLink edge : edges) {
                ConveyorNode v = edge.getTo();

                double weight = edge.getCost();
                double distanceThroughU = u.getMinDistance() + weight;

                if (distanceThroughU < v.getMinDistance()) {
                    v.setMinDistance(distanceThroughU);
                    v.setPrevious(u);
                    vertexQueue.remove(v);
                    vertexQueue.add(v);
                }
            }
        }

        Collections.reverse(shortestPath);

        return shortestPath;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (ConveyorNode node : neighbors.keySet()) {
            sb.append("\n    " + node.getNodeId().getValue() + " -> " + neighbors.get(node));
        }
        return sb.toString();
    }

    public Map<ConveyorNode, List<ConveyorLink>> getNeighbors() {
        return neighbors;
    }

}
