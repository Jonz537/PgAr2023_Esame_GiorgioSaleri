package main;

import world_stuff.TamaWorld;
import world_stuff.TaxWorld;
import world_stuff.World;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static final int WOLRDS_NUMBERS = 1;

    public static void main(String[] args) {
        ArrayList<World> worlds = new ArrayList<>();
        Player isekaiMc = UserInterface.createPlayer();




    }

    public static ArrayList<World> generateWorlds() {
        ArrayList<World> worlds = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < WOLRDS_NUMBERS; i++) {
            switch (random.nextInt(0,3)) {
                case 0 -> {
                    worlds.add(new World());
                }
                case 1 -> {
                    worlds.add(new TaxWorld());
                }
                case 2 -> {
                    worlds.add(new TamaWorld());
                }
            }
        }

        return worlds;
    }
}