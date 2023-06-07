package main;

import unibs.MenuManager;
import utils_bs.NodeType;
import utils_bs.XmlUtils;
import world_stuff.Node;
import world_stuff.TamaWorld;
import world_stuff.TaxWorld;
import world_stuff.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Main {

    public static final int WOLRDS_NUMBERS = 1;
    public static String[] worldTypeList = new String[WOLRDS_NUMBERS];
    public static ArrayList<Node> defaultMap = new ArrayList<>();


    public static void main(String[] args) {
        UserInterface.printManySpaces();
        System.out.println("Welcome to \"that time that I've been transported to another world" +
                " while I was doing an exam\"");

        createDefaultMap();

        ArrayList<World> worlds = generateWorlds();
        Player isekaiMc = UserInterface.createPlayer();
        MenuManager worldManager = new MenuManager("Which world you want to go to?", worldTypeList);

        while (UserInterface.checkForGameOver(isekaiMc, worlds)) {
            int selectedWorld = worldManager.chooseNoExit();
            worlds.get(selectedWorld).start(isekaiMc);
        }

        System.out.println("Bye-bye");
    }

    private static ArrayList<World> generateWorlds() {
        ArrayList<World> worlds = new ArrayList<>();
        Random random = new Random();

        ArrayList<ArrayList<Node>> maps = XmlUtils.readMaps("Mappe.xml");
        maps.add(defaultMap);

        for (int i = 0; i < WOLRDS_NUMBERS; i++) {
//            switch (random.nextInt(0,3)) {
            switch (random.nextInt(2,3)) {
                case 0 -> worlds.add(new World(maps.get(random.nextInt(0,3))));
                case 1 -> worlds.add(new TaxWorld(maps.get(random.nextInt(0,3))));
                case 2 -> {
//                    worlds.add(new TamaWorld(maps.get(random.nextInt(0,3))));
                    worlds.add(new TamaWorld(defaultMap));
                }
            }
            worldTypeList[i] = worlds.get(i).getType();
        }
        return worlds;
    }

    private static void createDefaultMap() {
        defaultMap.add(new Node(0, NodeType.INTERMEDIO, new HashSet<>(List.of(1))));
        defaultMap.add(new Node(1, NodeType.INIZIO, new HashSet<>(List.of(2, 3))));
        defaultMap.add(new Node(2, NodeType.INTERMEDIO, new HashSet<>(List.of(4))));
        defaultMap.add(new Node(3, NodeType.INTERMEDIO, new HashSet<>(List.of(4))));
        defaultMap.add(new Node(4, NodeType.INTERMEDIO, new HashSet<>(List.of(5))));
        defaultMap.add(new Node(5, NodeType.INTERMEDIO, new HashSet<>(List.of(6))));
        defaultMap.add(new Node(6, NodeType.FINE, new HashSet<>(List.of())));
    }
}