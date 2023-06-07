package world_stuff;

import evasione_fiscale_simulator.Person;
import main.Player;
import main.UserInterface;
import unibs.InputInterface;
import unibs.MenuManager;
import utils_bs.NodeType;
import utils_bs.XmlUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TaxWorld extends World{

    private static final int FINAL_TAX = 2200;

    static private ArrayList<Person> people = new ArrayList<>();
    static private HashMap<String, String> cities = new HashMap<>();
    private Calendar today;

    public TaxWorld(ArrayList<Node> map) {
        super(map);
        today = new GregorianCalendar(2023, Calendar.JUNE, 7);
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
        isekaiMc.setMoney(1100);

        int nextPos = 0;

        UserInterface.printMoneyPlayer(isekaiMc);
        today.add(Calendar.DATE, 1);

        do {
            map.get(isekaiMc.getCurrentPosition()).setVisited(true);

            MenuManager menuManager = new MenuManager("Where do you want to go?\nn - id",
                    getNextChoices(isekaiMc));

            UserInterface.printPetAdivce(map.get(isekaiMc.getCurrentPosition()), map);

            nextPos = Integer.parseInt(menuManager.chooseStringNoExit());

            exploreNode(isekaiMc, nextPos);
            isekaiMc.setCurrentPosition(nextPos);

            UserInterface.printMoneyPlayer(isekaiMc);
            today.add(Calendar.DATE, 1);

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
        UserInterface.printDate(today);
        Person personToCheck = people.get(random.nextInt(0, people.size()));

        boolean isLegal = personToCheck.validTaxId() && (personToCheck.getExpireDate().after(today));

        UserInterface.printPersonIdInfo(personToCheck);

        boolean choice = InputInterface.askTrueOrFalse("Is this person to fine?", "yes", "no");

        if (!isLegal && choice) {
            corruption(isekaiMc);
        }else if (isLegal && !choice ) {
            UserInterface.printRightChoiceTax();
        } else {
            errorTax(isekaiMc);
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void errorTax(Player isekaiMc) {
        isekaiMc.addMoney(-300);
        UserInterface.printWrongChoiceTax();
    }

    public void corruption(Player isekaiMc) {
        boolean isAmicoDelleGuardie = random.nextInt(0, 10) > 8;
        int bigMoney = random.nextInt(250, 501);
        boolean choice = InputInterface.askTrueOrFalse("I'll give yo' " + bigMoney + "§ if yo' let me git", "yes", "no");

        if (isAmicoDelleGuardie && choice) {
            isekaiMc.setTaxEvader(true);
        } else if (!isAmicoDelleGuardie && choice){
            isekaiMc.addMoney(bigMoney);
            UserInterface.printSuccessfulCorruption();
        }
    }

    public void goToJail(Player isekaiMc) {
        isekaiMc.lostLife();
        UserInterface.printJail(isekaiMc);
    }

    @Override
    public String getType() {
        return "Tax Evasion simulator";
    }
}
