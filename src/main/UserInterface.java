package main;

import unibs.InputInterface;
import world_stuff.World;

import java.util.ArrayList;

public class UserInterface {

    public static Player createPlayer() {
        return new Player(InputInterface.readNotEmptyStringSingleWord("Insert your name: "));
    }

    public static boolean checkForGameOver(Player isekaiMc, ArrayList<World> worlds) {
        if (isekaiMc.getLives() <= 0) {
            System.out.println("You died!");
            return false;
        }
        if(worlds.isEmpty()) {
            System.out.println("You survived, now go back to your dull life");
            return false;
        }
        return true;
    }

    public static void printPlayerStatus(Player isekaiMc) {
        System.out.println(isekaiMc.getName() + ": \n" +
                "health: " + isekaiMc.getHealthPoint() + ": \n" +
                "attack: " + isekaiMc.getAttack());
    }
}
