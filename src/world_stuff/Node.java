package world_stuff;

import utils_bs.NodeType;

import java.util.ArrayList;

public class Node {
    private int id;
    private boolean isVisited;

    private NodeType type;
    private Monster boss;

    private ArrayList<Integer> adjacentNodes = new ArrayList<>();

}
