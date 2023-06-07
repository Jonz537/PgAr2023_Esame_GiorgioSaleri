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
import java.util.concurrent.TimeUnit;

public class UserInterface {

    public static void printManySpaces() {
        System.out.println("\n\n\n");
    }

    public static void waitTime() {
        // waiting
        try {
            TimeUnit.MILLISECONDS.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Player createPlayer() {
        return new Player(InputInterface.readNotEmptyStringSingleWord("Insert your name: "));
    }

    public static boolean checkForGameOver(Player isekaiMc, ArrayList<World> worlds) {
        printManySpaces();
        if (isekaiMc.getLives() <= 0) {
            System.out.println("You died!");
            waitTime();
            return false;
        }
        if (worlds.isEmpty()) {
            System.out.println("You survived, now go back to your dull life");
            waitTime();
            return false;
        }
        return true;
    }

    public static void printPlayerStatus(Player isekaiMc) {
        System.out.println(isekaiMc.getName() + ": \n" +
                "health: " + isekaiMc.getHealthPoint() + " \n" +
                "attack: " + isekaiMc.getAttack());
        waitTime();
    }

    public static void printInteractEvent(int mod, EventType eventType) {
        System.out.println("You interacted with " + eventType.toString() + " with a modifier: " + mod);
        waitTime();
    }

    public static void printDeath(Player isekaiMc) {
        printManySpaces();
        System.out.println("You died... once\nLives left: " + isekaiMc.getLives());
        waitTime();
    }

    public static void printJailTax(Player isekaiMc) {
        printManySpaces();
        System.out.println("TAX EVASION IS A CRIME VERGIL!\nLives left: " + isekaiMc.getLives());
        waitTime();
    }

    public static void printJail(Player isekaiMc) {
        printManySpaces();
        System.out.println("You've been given a death sentence for corruption\nLives left: " + isekaiMc.getLives());
        waitTime();
    }

    public static void printWorldDefeated(Player isekaiMc) {
        printManySpaces();
        System.out.println("You survived this world \nPoints: " + isekaiMc.getPoints());
        waitTime();
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
        waitTime();
    }

    public static void printWonFight(Player isekaiMc) {
        System.out.println("The cursed champions falls!");
        waitTime();
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
        waitTime();
    }

    public static void printRightChoiceTax(Player isekaiMc) {
        printManySpaces();
        System.out.println("You did a great job today, " + isekaiMc.getName());
        waitTime();
    }

    public static void printWrongChoiceTax() {
        System.out.println("YOU MADE A MISTAKE YOU FOOL");
        waitTime();
    }

    public static void printMoneyPlayer(Player isekaiMc) {
        System.out.println("You have: " + isekaiMc.getMoney() + "§");
        waitTime();
    }

    public static void printSuccessfulCorruption() {
        System.out.println("Thanks yo'. Yo' saved me there ");
        waitTime();
    }

    public static void printDeathTamaWorld(Player isekaiMc) {
        System.out.println("You ran out of tamagolem! You are defenceless and died a horrible death ");
        waitTime();
    }

    public static void printPlayerStatusGolem(Player isekaiMc) {
        System.out.println(isekaiMc.getName() + " has " + isekaiMc.getTamaGolems().size() + " tamagolems");
        waitTime();
    }

    public static void printTamagolemStatus(TamaGolem tamaGolem) {
        System.out.println(tamaGolem.toString());
        waitTime();
    }

    public static void maxHealthGolem() {
        System.out.println("Cheers to you! All your stones are now full hp!");
        waitTime();
    }

    public static void deathOneTamagolem(Player isekaiMc) {
        System.out.println("Bad event: one of your pokemon...ehm, tamagolem is dead!");
        System.out.println("Tamagolem left: " + isekaiMc.getTamaGolems().size());
        waitTime();
    }

    public static String menuChooseGem(HashMap<String, Integer> choices, Player player, int num_tama, int num__gem) {

        System.out.println("\n" + player.getName() + " choose gem number " + num__gem + " for tamagolem number " + num_tama + ":");

        for (int i = 0; i < choices.size(); i++) {
            System.out.println(i + 1 + " - " + Universe.elements.get(i) + " (" + choices.get(Universe.elements.get(i)) + ")");
        }

        return Universe.elements.get(InputInterface.readInt("Choice: ", 1, choices.size()) - 1);
    }

    public static void mcGetDamageTama(Player isekaiMc, int damage, int tamaIndex) {
        System.out.println("Enemy tamagolem inflicted " + (-damage) + " damage to the " + isekaiMc.getName() + "'s tamagolem");
        System.out.println(isekaiMc.getName() + " your tamagolem has " + isekaiMc.getTamaGolems().get(tamaIndex).getHealthPoint() + " hp left");
        System.out.println();
        waitTime();
    }

    public static void mcDealsDamageTama(Player isekaiMc, int damage, TamaGolem enemy) {
        System.out.println(isekaiMc.getName() + "'s tamagolem inflicted " + damage + " damage to the enemy's tamagolem");
        System.out.println("enemy tamagolem has " + enemy.getHealthPoint() + " hp left");
        System.out.println();
        waitTime();
    }

    public static void tamagolemDeathByFight(Player isekaiMc) {
        System.out.println(isekaiMc.getName() + "'s tamagolem is dead (tamagolem's left " + (isekaiMc.getTamaGolems().size()) + ")");
        System.out.println();
        waitTime();
    }

    public static void winSingleFightTama() {
        System.out.println("You destroyed that pile of rocks");
        waitTime();
    }

    public static void winSingleMatchTama() {
        System.out.println("You also won this match, impressive!");
    }

    public static void printEnemyStones(TamaGolem enemy) {
        System.out.println("the enemy has those stones:");
        enemy.getGems().forEach(UserInterface::printGem);
        waitTime();
    }

    public static void printGem(int gem) {
        System.out.println(Universe.elements.get(gem));
        waitTime();
    }

    public static void printDoubleDamageNotification() {
        System.out.println("There has been a turbulence in the force! Alla damages are doubled");
        waitTime();
    }

    public static int chooseElementalGem(HashMap<String, Integer> choices, String message) {

        System.out.println(message);

        for (int i = 0; i < choices.size(); i++) {
            System.out.println(i + 1 + " - " + Universe.elements.get(i));
        }

        return InputInterface.readInt("Choice: ", 1, choices.size()) - 1;
    }
}
