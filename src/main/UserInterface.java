package main;

import monsters.Cammo;
import monsters.Monster;
import unibs.InputInterface;
import world_stuff.EventType;
import world_stuff.World;

import java.util.ArrayList;

public class UserInterface {

    public static void printManySpaces() {
        System.out.println("\n\n\n");
    }

    public static Player createPlayer() {
        return new Player(InputInterface.readNotEmptyStringSingleWord("Insert your name: "));
    }

    public static boolean checkForGameOver(Player isekaiMc, ArrayList<World> worlds) {
        printManySpaces();
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

    public static void printInteractEvent(int mod, EventType eventType) {
        System.out.println("You interacted with " + eventType.toString() + " with a modifier: " + mod);
    }

    public static void printDeath(Player isekaiMc) {
        printManySpaces();
        System.out.println("You died... once\nLives left: " + isekaiMc.getLives());
    }

    public static void printWorldDefeated(Player isekaiMc) {
        printManySpaces();
        System.out.println("You survived this world \nPoints: " + isekaiMc.getPoints());
    }

    public static void printBattleInfo(Player isekaiMc, Monster boss) {

        printManySpaces();
        printPlayerStatus(isekaiMc);
        if (boss instanceof Cammo) {
            System.out.print("Cammo:\n");
        } else {
            System.out.print("No name monster:\n");
        }
        System.out.println("health: " + boss.getHealthPoints() + ": \n" +
                "attack: " + boss.getAttack());

    }

    public static void printWonFight(Player isekaiMc) {
        System.out.println("The cursed champions falls!");
    }
}
