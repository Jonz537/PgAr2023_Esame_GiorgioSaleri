package world_stuff;

import utils_bs.NodeType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Node {
    private int id;
    private boolean isVisited;

    private NodeType type;

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
}
