package event_handler;

import main.Player;
import main.UserInterface;
import tamagolem.TamaGolem;
import tamagolem.Universe;
import utils_bs.EventType;


import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TamaEvent extends Event {

    HashMap<String, Integer> gems;

    public TamaEvent(HashMap<String, Integer> gems) {
        switch (random.nextInt(0,2)) {
            case 0 -> {
                eventType = EventType.TAMAHEALTH;
                mod = random.nextInt(0, 2);
            }
            case 1 -> {
                eventType = EventType.TAMAELEMENT;
                mod = random.nextInt(0, 3);
            }
        }
        this.gems = gems;
    }

    @Override
    public void playerInteract(Player isekaiMc) {
        if (eventType.equals(EventType.TAMAHEALTH)) {
            if (mod == 0) {
                isekaiMc.getTamaGolems().forEach(TamaGolem::setMaxHealth);
                UserInterface.maxHealthGolem();
            } else {
                isekaiMc.getTamaGolems().remove(random.nextInt(0, isekaiMc.getTamaGolems().size()));
                UserInterface.deathOneTamagolem(isekaiMc);
            }
        } else {
            switch (random.nextInt(0, 3)) {
                case 0 -> {
                    Universe.strongerForces(gems);
                }
                case 1 -> {
                    Universe.weakerForces(gems);
                }
                case 2 -> {
                    Universe.doubleBalance();
                    UserInterface.printDoubleDamageNotification();
                }

            }
        }
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
