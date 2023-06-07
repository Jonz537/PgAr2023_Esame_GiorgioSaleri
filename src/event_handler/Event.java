package event_handler;

import main.Player;
import main.UserInterface;
import utils_bs.EventType;

import java.util.Random;

public class Event {

    protected EventType eventType;
    protected int mod;
    protected Random random = new Random();
    /**
     * create a random event
     */
    public Event() {

        switch (random.nextInt(0,2)) {
            case 0 -> {
                eventType = EventType.DAMAGE;
                mod = random.nextInt(-5, 11);
            }
            case 1 -> {
                eventType = EventType.HEALTH;
                mod = random.nextInt(-3, 4);
            }
        }

        UserInterface.printInteractEvent(mod, eventType);
    }

    /**
     *  based on the event we modify player's stats
     * @param isekaiMc player
     */
    public void playerInteract(Player isekaiMc) {
        if (eventType.equals(EventType.DAMAGE)) {
            isekaiMc.addAttack(mod);
        } else {
            isekaiMc.addHealthPoint(mod);
        }
    }

}
