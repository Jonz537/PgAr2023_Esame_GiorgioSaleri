package main;

import tamagolem.TamaGolem;
import tamagolem.Universe;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    /**
     * Modulo 1
     */
    private String name;
    private int lives;
    private int points;
    private int currentPosition;
    private int healthPoint;
    private int attack;

    /**
     * 5.4.2 Modulo patente e libbretto prego
     */
    private boolean taxEvader = false;
    private int money = 1100;

    /**
     * 5.4.1 Modulo tamagolem
     */
    public static final int INITIAL_TAMAGOLEM_NUMBER = 3;
    private ArrayList<TamaGolem> tamaGolems = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        lives = 10;
        points = 0;
        currentPosition = 0;
        healthPoint = 20;
        attack = 5;
        money = 1100;
    }

    public void summonGolems(HashMap<String, Integer> gems) {
        for (int k = 0; k < INITIAL_TAMAGOLEM_NUMBER; k++) {

            TamaGolem tamaGolem = new TamaGolem();

            for (int i = 0; i < TamaGolem.GEMS_PER_GOLEM; i++) {

                String gemKey;

                do {
                    gemKey = UserInterface.menuChooseGem(gems, this, k, i);

                    if(gems.get(gemKey) <= 0) {
                        System.out.println("There are no more gems of \"" + gemKey + "\", you Bafoon!");
                    }
                } while(gems.get(gemKey) <= 0);

                tamaGolem.addGemToGolem(Universe.elements.indexOf(gemKey));
                gems.put(gemKey, gems.get(gemKey) - 1);
            }
            addTamaGolem(tamaGolem);
        }
    }

    public int getLives() {
        return lives;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int pointsToAdd) {
        points += pointsToAdd;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void lostLife() {
        this.lives--;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void addHealthPoint(int healthPointToAdd) {
        healthPoint += healthPointToAdd;
    }

    public int getAttack() {
        return attack;
    }

    public void addAttack(int attackToAdd) {
        attack += attackToAdd;
    }

    public boolean isTaxEvader() {
        return taxEvader;
    }

    public void setTaxEvader(boolean taxEvader) {
        this.taxEvader = taxEvader;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public ArrayList<TamaGolem> getTamaGolems() {
        return tamaGolems;
    }

    public static void maxHealthTama(TamaGolem tamaGolem) {
        tamaGolem.setMaxHealth();
    }

    public void addTamaGolem(TamaGolem tamaGolem) {
        tamaGolems.add(tamaGolem);
    }
}
