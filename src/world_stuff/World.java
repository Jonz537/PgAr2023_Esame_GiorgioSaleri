package world_stuff;

import main.Player;
import main.UserInterface;
import unibs.MenuManager;
import utils_bs.NodeType;
import utils_bs.XmlUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class World {

    private boolean isDefeated;

    private ArrayList<Node> map;

    public World(ArrayList<Node> map) {
        this.map = map;
        isDefeated = false;
    }

    public String getType() {
        return "Dungeon crawler";
    }

    public void start(Player isekaiMc) {

        doubleGraph();

        int nextPos = 0;

        do {
            map.get(isekaiMc.getCurrentPosition()).setVisited(true);
            MenuManager menuManager = new MenuManager("Where do you want to go?",
                    getNextChoices(isekaiMc));
            nextPos = menuManager.chooseNoExit();

            battle(isekaiMc);

            isekaiMc.setCurrentPosition(nextPos);
        } while (!map.get(isekaiMc.getCurrentPosition()).getType().equals(NodeType.FINE)
        || isekaiMc.getHealthPoint() >= 0);

    }

    public void battle(Player isekaiMc) {
        UserInterface.printPlayerStatus(isekaiMc);


    }

    public String[] getNextChoices(Player isekaiMc) {
        ArrayList<String> aString = new ArrayList<>();
        // TODO vicolo cieco
        for (Integer i: map.get(isekaiMc.getCurrentPosition()).getAdjacentNodes()) {
            if (!map.get(i).isVisited()) {
                aString.add(i.toString());
            }
        }

        String[] choices = new String[aString.size()];
        for (int i = 0; i < aString.size(); i++) {
            choices[i] = aString.get(i);
        }

        return choices;
    }

    public void doubleGraph() {
        for (Node node: map) {
            for (Integer i: node.getAdjacentNodes()) {
                map.get(i).addAdjacentNode(node.getId());
            }
        }
    }
}
