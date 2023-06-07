package world_stuff;

import evasione_fiscale_simulator.Person;
import main.Player;
import main.UserInterface;
import unibs.MenuManager;
import utils_bs.NodeType;
import utils_bs.XmlUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class TaxWorld extends World{

    static private ArrayList<Person> people = new ArrayList<>();
    static private HashMap<String, String> cities = new HashMap<>();

    public TaxWorld(ArrayList<Node> map) {
        super(map);
        if (people.isEmpty() || cities.isEmpty()) {
            cities = XmlUtils.readCities("Comuni.xml");
            people = XmlUtils.readPeople("PersoneID.xml");
        }
    }

    public static String getCitiesByName(String name) {
        return cities.getOrDefault(name, null);
    }

    @Override
    public void start(Player isekaiMc) {
        map.forEach(Node::setUnvisited);
        doubleGraph();
        isekaiMc.setCurrentPosition(0);

        int nextPos = 0;

        do {
            map.get(isekaiMc.getCurrentPosition()).setVisited(true);
            MenuManager menuManager = new MenuManager("Where do you want to go?",
                    getNextChoices(isekaiMc));


            UserInterface.printPetAdivce(map.get(isekaiMc.getCurrentPosition()), map);
            nextPos = Integer.parseInt(menuManager.chooseStringNoExit());

            exploreNode(isekaiMc, nextPos);

            isekaiMc.setCurrentPosition(nextPos);

            if (map.get(isekaiMc.getCurrentPosition()).getType().equals(NodeType.FINE)) {
                worldDefeated(isekaiMc);
                break;
            } else if (isekaiMc.isTaxEvader()) {
                goToJail(isekaiMc);
                break;
            }

        } while (true);
    }

    @Override
    public void exploreNode(Player isekaiMc, int battlePos) {

    }

    public void goToJail(Player isekaiMc) {
        isekaiMc.lostLife();
        UserInterface.printJail(isekaiMc);
    }

}
