package world_stuff;

import main.Player;
import main.UserInterface;
import unibs.MenuManager;
import utils_bs.NodeType;

import java.util.ArrayList;

public class TamaWorld extends World{

    public TamaWorld(ArrayList<Node> map) {
        super(map);
    }

    @Override
    public void start(Player isekaiMc) {

        map.forEach(Node::setUnvisited);
        doubleGraph();
        isekaiMc.setCurrentPosition(0);

        int nextPos = 0;

        do {
            map.get(isekaiMc.getCurrentPosition()).setVisited(true);
            MenuManager menuManager = new MenuManager("Where do you want to go?\nn - id",
                    getNextChoices(isekaiMc));


            UserInterface.printPetAdivce(map.get(isekaiMc.getCurrentPosition()), map);
            nextPos = Integer.parseInt(menuManager.chooseStringNoExit());

            exploreNode(isekaiMc, nextPos);

            isekaiMc.setCurrentPosition(nextPos);

            if (map.get(isekaiMc.getCurrentPosition()).getType().equals(NodeType.FINE)) {
                worldDefeated(isekaiMc);
                break;
            } else if (isekaiMc.getHealthPoint() <= 0) {
                death(isekaiMc);
                break;
            }

        } while (true);

    }

    @Override
    public String getType() {
        return "Tamagolem World";
    }
}
