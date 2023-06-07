package main;

public class Player {

    private String name;
    private int lives;
    private int points;
    private int currentPosition;
    private int healthPoint;
    private int attack;

    public Player(String name) {
        this.name = name;
        lives = 10;
        points = 0;
        currentPosition = 0;
        healthPoint = 20;
        attack = 5;
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

    public void setLives(int lives) {
        this.lives = lives;
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
}
