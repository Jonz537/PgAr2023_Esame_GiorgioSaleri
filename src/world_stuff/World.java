package world_stuff;

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

    public void exploreNode(Player isekaiMc, int battlePos) {
        UserInterface.printPlayerStatus(isekaiMc);

        if (map.get(battlePos).getType().equals(NodeType.FINE)) {
            battle(isekaiMc, new Cammo());
        } else {
            switch (random.nextInt(0,2)) {
                case 0 -> {
                    new Event().playerInteract(isekaiMc);
                }
                case 1 -> {
                    battle(isekaiMc, new Monster());
                }
            }
        }

    }

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

            // waiting
            try {
                TimeUnit.MILLISECONDS.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (boss.getHealthPoints() < 0) {
            UserInterface.printWonFight(isekaiMc);
        }
    }

    public void death(Player isekaiMc) {
        isekaiMc.lostLife();
        UserInterface.printDeath(isekaiMc);
    }

    public void worldDefeated(Player isekaiMc) {
        if (!isDefeated) {
            isekaiMc.addPoints(10);
        }
        UserInterface.printWorldDefeated(isekaiMc);
    }

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


    public void doubleGraph() {
        for (Node node: map) {
            for (Integer i: node.getAdjacentNodes()) {
                map.get(i).addAdjacentNode(node.getId());
            }
        }
    }
}
