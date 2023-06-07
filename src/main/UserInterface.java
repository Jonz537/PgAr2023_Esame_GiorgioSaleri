package main;

import evasione_fiscale_simulator.Person;
import monsters.Cammo;
import monsters.Monster;
import tamagolem.TamaGolem;
import tamagolem.Universe;
import unibs.InputInterface;
import utils_bs.Dijkstra;
import utils_bs.EventType;
import world_stuff.Node;
import world_stuff.TamaWorld;
import world_stuff.World;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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

    public static void printJailTax(Player isekaiMc) {
        printManySpaces();
        System.out.println("TAX EVASION IS A CRIME VERGIL!\nLives left: " + isekaiMc.getLives());
    }

    public static void printJail(Player isekaiMc) {
        printManySpaces();
        System.out.println("You've been given a death sentence for corruption\nLives left: " + isekaiMc.getLives());
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

    public static void printPetAdivce(Node node, ArrayList<Node> map) {
        Dijkstra.pathFinder(node, map);
        printManySpaces();
        System.out.println("Pet: \"The nyext best muv is the nyode with id " + Dijkstra.returnNextMove(map.get(map.size() - 1)) + ", nyah!\"");
    }

    public static void printDate(Calendar today) {
        System.out.println("today is: " + today.get(Calendar.DAY_OF_MONTH) + "-"
                + (today.get(Calendar.MONTH) + 1) + "-"
                + today.get(Calendar.YEAR));
    }

    public static void printPersonIdInfo(Person personToCheck) {
        System.out.println(personToCheck.toString());
    }

    public static void printRightChoiceTax(Player isekaiMc) {
        printManySpaces();
        System.out.println("You did a great job today, " + isekaiMc.getName());
    }

    public static void printWrongChoiceTax() {
        System.out.println("YOU MADE A MISTAKE YOU FOOL");
    }

    public static void printMoneyPlayer(Player isekaiMc) {
        System.out.println("You have: " + isekaiMc.getMoney() + "§");
    }

    public static void printSuccessfulCorruption() {
        System.out.println("Thanks yo'. Yo' saved me there ");
    }

    public static void printDeathTamaWorld(Player isekaiMc) {
        System.out.println("You ran out of tamagolem! You are defenceless and died a horrible death ");
    }

    public static void printPlayerStatusGolem(Player isekaiMc) {
        System.out.println(isekaiMc.getName() + " has " + isekaiMc.getTamaGolems().size());
    }

    public static void printTamagolemStatus(TamaGolem tamaGolem) {
        System.out.println(tamaGolem.toString());
    }

    public static void maxHealthGolem() {
        System.out.println("Cheers to you! All your stones are now full hp!");
    }

    public static void deathOneTamagolem(Player isekaiMc) {
        System.out.println("Bad event: one of your pokemon...ehm, tamagolem is dead!");
        System.out.println("Tamagolem left: " + isekaiMc.getTamaGolems().size());
    }

    public static String menuChooseGem(HashMap<String, Integer> choices, Player player, int num_tama, int num__gem) {

        System.out.println("\n" + player.getName() + " choose gem number " + num__gem +" for tamagolem number " + num_tama + ":");

        for (int i = 0; i < choices.size(); i++) {
            System.out.println(i + 1 + " - " + Universe.elements.get(i) + " (" + choices.get(Universe.elements.get(i)) + ")");
        }

        return Universe.elements.get(InputInterface.readInt("Choice: ", 1, choices.size()) - 1);
    }
}
