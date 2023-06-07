package world_stuff;

import utils_bs.NodeType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Node {

    /**
     * world's stuff
     */
    private int id;
    private boolean isVisited;
    private NodeType type;

    /**
     * dijkstra's stuff
     */
    private boolean isVisitedByDijkstra;
    private Set<Integer> adjacentNodes = new HashSet<>() {
    };

    public Node(int id) {
        this.id = id;
        isVisited = false;
    }

    public Node(int id, NodeType type, Set<Integer> adjacentNodes) {
        this.id = id;
        this.type = type;
        this.adjacentNodes = adjacentNodes;
        isVisited = false;

    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public void addAdjacentNode(int node) {
        adjacentNodes.add(node);
    }

    public void addAdjacentNode(ArrayList<Integer> nodes) {
        adjacentNodes.addAll(nodes);
    }

    public Set<Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public int getId() {
        return id;
    }

    public boolean isVisitedByDijkstra() {
        return isVisitedByDijkstra;
    }

    public static void setUnvisited(Node node) {
        node.setVisited(false);
    }

    public void setVisitedByDijkstra(Boolean visited) {
        isVisitedByDijkstra = visited;
    }

    public static void setVisitedByDijkstra(Node node) {
        node.setVisitedByDijkstra(false);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
