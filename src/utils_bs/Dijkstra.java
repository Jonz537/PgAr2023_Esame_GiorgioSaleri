package utils_bs;

import world_stuff.Node;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;

public class Dijkstra {


    private static Deque<Node> deque = new ArrayDeque<>();
    private static HashMap<Node, Double> distanceFromStart = new HashMap<>();
    private static HashMap<Node, ArrayList<Node>> pathFromStart = new HashMap<>();

    public static void pathFinder(Node start, ArrayList<Node> map) {

        // settiamo ogni città come non visitata
        map.forEach(Node::setVisitedByDijkstra);

        Node currentNode;
        deque.add(start);
        map.get(start.getId()).setVisitedByDijkstra(true);

        distanceFromStart.put(start, 0.0);

        // controlliamo che non ci sono più città rimanenti
        while (deque.size() > 0) {
            currentNode = deque.poll();
            // ciclo per controllare le città adiacenti
            for (Integer i: currentNode.getAdjacentNodes()) {

                // aggiungiamo alla lista delle città da visitare le città non visitate
                if (!map.get(i).isVisitedByDijkstra()) {
                    deque.add(map.get(i));
                    map.get(i).setVisitedByDijkstra(true);
                }

                // calcoliamo la distanza dal nodo attuale con il nodo successivo
                double currentDistancePlusOne =
                        distanceFromStart.getOrDefault(currentNode, Double.MAX_VALUE / 2) + 1;

                // distanza dall'inizio del nodo attuale + distanza dal prossimo è minore dalla distanza dal prossimo dall'inizo
                if (currentNode.equals(start)) {
                    // aggiungiamo le città iniziali
                    distanceFromStart.put(map.get(i), 1.0);

                    ArrayList<Node> initialPath = new ArrayList<>();
                    initialPath.add(currentNode);
                    pathFromStart.put(map.get(i), initialPath);
                } else if (currentDistancePlusOne < distanceFromStart.getOrDefault(map.get(i), Double.MAX_VALUE/2)) {
                    // aggiorniamo la distanza del prossimo
                    distanceFromStart.put(map.get(i), distanceFromStart.getOrDefault(currentNode, Double.MAX_VALUE) + 1);

                    // aggiorniamo le citta visitate
                    ArrayList<Node> updatePath = new ArrayList<>(pathFromStart.get(currentNode));
                    updatePath.add(currentNode);
                    pathFromStart.put(map.get(i), updatePath);
                    map.get(i).setVisitedByDijkstra(false);

                }
            }
        }
    }

    public static HashMap<Node, ArrayList<Node>> getPathFromStart() {
        return pathFromStart;
    }

    public static String returnNextMove(Node node) {
        return pathFromStart.get(node).get(0).toString();
    }

}
