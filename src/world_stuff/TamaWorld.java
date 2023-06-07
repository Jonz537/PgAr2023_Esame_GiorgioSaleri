package world_stuff;

import event_handler.Event;
import main.Player;
import main.UserInterface;
import tamagolem.TamaGolem;
import tamagolem.Universe;
import unibs.InputInterface;
import unibs.MenuManager;
import utils_bs.NodeType;

import java.util.ArrayList;
import java.util.HashMap;

public class TamaWorld extends World{

    public static int NUM_ELEMENT = Universe.elements.size();
    public static int INITIAL_NUM_GOLEM = (int) Math.ceil(((TamaWorld.NUM_ELEMENT - 1) * (TamaWorld.NUM_ELEMENT - 2)) / (2.0 * TamaGolem.GEMS_PER_GOLEM));
    public static int TOT_GEM_BAG = (int) Math.ceil((2.0 * INITIAL_NUM_GOLEM * TamaGolem.GEMS_PER_GOLEM) / NUM_ELEMENT) * NUM_ELEMENT;
    private HashMap<String, Integer> gems = new HashMap<>();

    public TamaWorld(ArrayList<Node> map) {
        super(map);
    }

    @Override
    public void start(Player isekaiMc) {

        map.forEach(Node::setUnvisited);

        // generate equilibrium only at the start of the world
        Universe.generateEquilibrium();

        // set up the graph
        doubleGraph();
        isekaiMc.setCurrentPosition(0);

        // let the player summon the tamagolem
        initializeGemHashMap();
        isekaiMc.summonGolems(gems);

        int nextPos = 0;

        do {
            nextPos = playerMovement(isekaiMc);

            if (map.get(isekaiMc.getCurrentPosition()).getType().equals(NodeType.FINE)) {
                worldDefeated(isekaiMc);
                break;
            } else if (isekaiMc.getTamaGolems().size() == 0) {
                death(isekaiMc);
                break;
            }

        } while (true);

    }

    @Override
    public void death(Player isekaiMc) {
        isekaiMc.lostLife();
        UserInterface.printDeathTamaWorld(isekaiMc);
    }

    @Override
    public void exploreNode(Player isekaiMc, int battlePos) {
        UserInterface.printPlayerStatusGolem(isekaiMc);

        System.out.println("monke");
        if (map.get(battlePos).getType().equals(NodeType.FINE)) {
            // todo final tamagolem fight
        } else {
            switch (random.nextInt(0,2)) {
                case 0 -> {
                    // todo random mod
                    new Event().playerInteract(isekaiMc);
                }
                case 1 -> {
                    //todo tamagolem fight
                }
            }
        }
    }

    private void initializeGemHashMap() {
        for (int i = 0; i < NUM_ELEMENT; i++) {
            gems.put(Universe.elements.get(i), TOT_GEM_BAG/NUM_ELEMENT);
        }
    }

    @Override
    public String getType() {
        return "Tamagolem World";
    }
}
