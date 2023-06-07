package world_stuff;

import event_handler.TamaEvent;
import main.Player;
import main.UserInterface;
import tamagolem.TamaGolem;
import tamagolem.Universe;
import unibs.InputInterface;
import utils_bs.NodeType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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

        if (map.get(battlePos).getType().equals(NodeType.FINE)) {
            // create 2 random golem to fight
            Deque<TamaGolem> tamaGolems = new ArrayDeque<>();

            TamaGolem tamagolem1 = new TamaGolem();
            tamagolem1.generateRandomStones();
            tamaGolems.add(tamagolem1);

            TamaGolem tamagolem2 = new TamaGolem();
            tamagolem2.generateRandomStones();
            tamaGolems.add(tamagolem2);

            tamaFight(isekaiMc, tamaGolems, true);
        } else {
            switch (random.nextInt(0,1)) {
                case 0 -> {
                    new TamaEvent(gems).playerInteract(isekaiMc);
                }
                case 1 -> {
                    Deque<TamaGolem> tamaGolems = new ArrayDeque<>();

                    TamaGolem tamagolem = new TamaGolem();
                    tamagolem.generateRandomStones();
                    tamaGolems.add(tamagolem);

                    tamaFight(isekaiMc, tamaGolems, false);
                }
            }
        }
    }

    private void tamaFight(Player isekaiMc, Deque<TamaGolem> enemyTamagolems, boolean isfinalBoss) {

        while (true) {

            isekaiMc.getTamaGolems().forEach(UserInterface::printTamagolemStatus);
            int tamaIndex = InputInterface.readInt("Choose your tamagolem between 0 and " +
                            (isekaiMc.getTamaGolems().size() - 1),
                    0,isekaiMc.getTamaGolems().size());

            TamaGolem enemy = enemyTamagolems.pop();
            while (true) {

                UserInterface.printEnemyStones(enemy);

                int damage = Universe.calcDamage(isekaiMc.getTamaGolems().get(tamaIndex), enemy, isfinalBoss);

                System.out.println(isekaiMc.getName() + " " + isekaiMc.getTamaGolems().get(tamaIndex).toString());
                System.out.println(enemy);
                System.out.println();

                if (damage < 0) {
                    // player 1 got damaged
                    isekaiMc.getTamaGolems().get(tamaIndex).receiveDamage(damage);
                    UserInterface.mcGetDamageTama(isekaiMc, damage, tamaIndex);
                } else if (damage > 0) {
                    // player 2 got damaged
                    enemy.receiveDamage(damage);
                    UserInterface.mcDealsDamageTama(isekaiMc, damage, enemy);
                }

                // tamagolem death
                if (isekaiMc.getTamaGolems().get(tamaIndex).getHealthPoint() <= 0) {
                    isekaiMc.getTamaGolems().remove(tamaIndex);
                    UserInterface.tamagolemDeathByFight(isekaiMc);

                    // player non more tamagolem
                    if(isekaiMc.getTamaGolems().size() == 0) {
                        return;
                    }
                    break;


                } else if (enemy.getHealthPoint() <= 0) {
                    // enemy died
                    UserInterface.winSingleFightTama();

                    // enemy lost all tamagolems
                    if (enemyTamagolems.size() == 0) {
                        UserInterface.winSingleFightTama();
                        return;
                    }
                    break;
                }
                // waiting
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
