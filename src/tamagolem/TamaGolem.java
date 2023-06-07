package tamagolem;

import unibs.MenuManager;
import world_stuff.TamaWorld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class TamaGolem {

    public static int GEMS_PER_GOLEM = (int) Math.ceil((TamaWorld.NUM_ELEMENT + 1) / 3.0) + 1;
    private int healthPoint;
    private ArrayList<Integer> gems = new ArrayList<>();

    /**
     * Create a tamaGolem with healthPoint of the max damage in the equilibrium
     */
    public TamaGolem() {
        this.healthPoint = Universe.getMaxDamage();
    }

    public ArrayList<Integer> getGems() {
        return gems;
    }


    /**
     * cycle the tamaGolem's gems
     * @return current gem
     */
    public int choseGem() {

        MenuManager menuManager = new MenuManager("Which gem this round?", gemToString());

        return menuManager.chooseNoExit();
    }

    public String[] gemToString() {
        String[] stringGem = new String[gems.size()];
        for (int i = 0; i < gems.size(); i++) {
            stringGem[i] = String.valueOf(gems.get(i));
        }
        return stringGem;
    }

    /**
     * add gems chosen by the player to his tamagolem
     * @param gemType gem type to add in the golem
     */
    public void addGemToGolem(int gemType) {
        gems.add(gemType);
    }

    /**
     * @return tamagolem health points
     */
    public int getHealthPoint() {
        return Math.max(healthPoint, 0);
    }

    /**
     * subtract health point based on damageTaken
     * @param damageHealthPoints damage taken from the tamagolem
     */
    public void receiveDamage(int damageHealthPoints) {
        healthPoint -= Math.abs(damageHealthPoints);
    }

    /**
     * @return a String with important tamagolem's stats
     */
    public String toString() {
        StringBuilder string = new StringBuilder("Golem ");
        string.append(healthPoint).append(" hp   ");

        for (Integer i: gems) {
            string.append(Universe.elements.get(i));
        }

        return string.toString();
    }

    /**
     * check if both linked list are equal
     * @param otherGolem the golem you want compare the gems with
     * @return true if all gems are equals in the same order
     */
    public boolean compareGems(TamaGolem otherGolem) {
        Iterator<Integer> thisIterator;
        Iterator<Integer> otherIterator;

        // seconda golem null
        try {
            thisIterator = gems.iterator();
            otherIterator = otherGolem.getGems().iterator();
        } catch (NullPointerException e) {
            return false;
        }

        // different size gems, this should never happen
        if (gems.size() != otherGolem.getGems().size()) {
            return false;
        }

        // check each gem one by one
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            if (!(thisIterator.next().intValue() == otherIterator.next().intValue())) {
                return false;
            }
        }
        return true;
    }

    public void setMaxHealth() {
        this.healthPoint = Universe.getMaxDamage();
    }
}
