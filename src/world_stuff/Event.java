package world_stuff;

import main.Player;

import java.util.Random;

public class Event {

    private EventType eventType;
    private int mod;

    public Event() {
        Random random = new Random();
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
    }

    public void playerInteract(Player isekaiMc) {
        if (eventType.equals(EventType.DAMAGE)) {
            isekaiMc.addAttack(mod);
        } else {
            isekaiMc.addHealthPoint(mod);
        }
    }

}
