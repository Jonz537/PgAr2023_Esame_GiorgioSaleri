package event_handler;

import main.Player;
import main.UserInterface;
import tamagolem.TamaGolem;
import utils_bs.EventType;
import world_stuff.TamaWorld;

public class TamaEvent extends Event {

    public TamaEvent() {
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
            // todo elemental event
        }

        UserInterface.printInteractEvent(mod, eventType);
    }
}
