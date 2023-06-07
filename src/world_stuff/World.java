package world_stuff;

import event_handler.Event;
import main.Player;
import main.UserInterface;
import monsters.Cammo;
import monsters.Monster;
import unibs.MenuManager;
import utils_bs.NodeType;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class World {

    protected boolean isDefeated;
    protected Random random = new Random();
    protected ArrayList<Node> map;

    public World(ArrayList<Node> map) {
        this.map = map;
        isDefeated = false;
    }

    public String getType() {
        return "Dungeon crawler";
    }

    public int playerMovement(Player isekaiMc) {
        int nextPos = 0;

        map.get(isekaiMc.getCurrentPosition()).setVisited(true);
        MenuManager menuManager = new MenuManager("Where do you want to go?\nn - id",
                getNextChoices(isekaiMc));

        // Dijkstra stuff here
        UserInterface.printPetAdivce(map.get(isekaiMc.getCurrentPosition()), map);

        // ask question
        nextPos = Integer.parseInt(menuManager.chooseStringNoExit());

        //explore the node the player chose
        this.exploreNode(isekaiMc, nextPos);

        //update player position
        isekaiMc.setCurrentPosition(nextPos);


        return nextPos;
    }

    public void start(Player isekaiMc) {
        map.forEach(Node::setUnvisited);
        // fix stuff for the graph
        doubleGraph();
        isekaiMc.setCurrentPosition(0);

        int nextPos = 0;

        do {
            // set visited so the player cannot go back
            nextPos = playerMovement(isekaiMc);

            if (map.get(isekaiMc.getCurrentPosition()).getType().equals(NodeType.FINE)) {
                worldDefeated(isekaiMc);
                break;
            } else if (isekaiMc.getHealthPoint() <= 0) {
                death(isekaiMc);
                break;
            }

        } while (true);
    }

    /**
     * regulate moving to next node
     * @param isekaiMc player
     * @param battlePos position of the node the player wants to go to
     */
    public void exploreNode(Player isekaiMc, int battlePos) {
        UserInterface.printPlayerStatus(isekaiMc);

        if (map.get(battlePos).getType().equals(NodeType.FINE)) {
            battle(isekaiMc, new Cammo());
        } else {
            switch (random.nextInt(0,2)) {
                case 0 -> {
                    //random modifier
                    new Event().playerInteract(isekaiMc);
                }
                case 1 -> {
                    battle(isekaiMc, new Monster());
                }
            }
        }
    }

    /**
     * if the player find a monster
     * @param isekaiMc player
     * @param boss the enemy to fight
     */
    public void battle(Player isekaiMc, Monster boss) {
        int round = 0;
        while(boss.getHealthPoints() > 0 && isekaiMc.getHealthPoint() > 0) {
            round = (round + 1) % 2;
            if (round == 1) {
                // player turn
                boss.getAttackedByPlayer(isekaiMc);
            } else {
                // boss turn
                boss.attackPlayer(isekaiMc);
            }
            UserInterface.printBattleInfo(isekaiMc, boss);
            
        }

        if (boss.getHealthPoints() < 0) {
            UserInterface.printWonFight(isekaiMc);
        }
    }

    /**
     * remove a life from the player andprint message
     * @param isekaiMc player
     */
    public void death(Player isekaiMc) {
        isekaiMc.lostLife();
        UserInterface.printDeath(isekaiMc);
    }

    /**
     * give points if the world was undefeated
     * @param isekaiMc player
     */
    public void worldDefeated(Player isekaiMc) {
        if (!isDefeated) {
            isekaiMc.addPoints(10);
            isDefeated = true;
        }
        UserInterface.printWorldDefeated(isekaiMc);
    }

    /**
     * make a list of adjacent nodes for the menu
     * @param isekaiMc plauer
     * @return strings of adjacent nodes
     */
    public String[] getNextChoices(Player isekaiMc) {
        ArrayList<String> aString = new ArrayList<>();
        // TODO vicolo cieco
        for (Integer i: map.get(isekaiMc.getCurrentPosition()).getAdjacentNodes()) {
            if (!map.get(i).isVisited()) {
                aString.add(map.get(i).toString());
            }
        }

        String[] choices = new String[aString.size()];
        for (int i = 0; i < aString.size(); i++) {
            choices[i] = aString.get(i);
        }

        return choices;
    }

    /**
     * make the graph bidirectional for Dijkstra
     */
    public void doubleGraph() {
        for (Node node: map) {
            for (Integer i: node.getAdjacentNodes()) {
                map.get(i).addAdjacentNode(node.getId());
            }
        }
    }
}
