package monsters;

import main.Player;

import java.util.Random;

public class Monster {

    protected int healthPoints;
    protected int attack;
    protected Random random = new Random();

    public Monster() {
        healthPoints = random.nextInt(7, 18);
        attack = random.nextInt(1, 6);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void getAttackedByPlayer(Player isekaiMc) {
        healthPoints -= isekaiMc.getAttack();
    }

    public void attackPlayer(Player isekaiMc) {
        isekaiMc.addHealthPoint(-attack);
    }
}
