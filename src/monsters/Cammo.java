package monsters;

public class Cammo extends Monster {

    public Cammo() {
        healthPoints = random.nextInt(13, 24);
        attack = random.nextInt(2, 7);
    }
}
