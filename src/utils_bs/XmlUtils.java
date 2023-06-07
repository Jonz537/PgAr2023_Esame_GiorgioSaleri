package utils_bs;

import evasione_fiscale_simulator.Person;
import evasione_fiscale_simulator.TaxIdCode;
import world_stuff.Node;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class XmlUtils {

    private static XMLInputFactory xmlIf = null;
    private static XMLStreamReader xmlR = null;

    private static void initializeXMLReader(String filename) {
        try {
            xmlIf = XMLInputFactory.newInstance();
            xmlR = xmlIf.createXMLStreamReader(filename, new FileInputStream(filename));
        } catch (Exception e) {
            System.out.println("Error in initializing XML stream reader:\n" + e.getMessage());
        }
    }

    /**
     * read maps from the xml
     * @param filename filename
     * @return 2 cities read
     */
    public static ArrayList<ArrayList<Node>> readMaps(String filename) {

        initializeXMLReader(filename);

        ArrayList<ArrayList<Node>> values = new ArrayList<>();

        ArrayList<Node> map = null;
        Node nodeToAdd = null;

        try {
            while (xmlR.hasNext()) {
                switch (xmlR.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        if (xmlR.getLocalName().equals("MAPPA")) {
                            map = new ArrayList<>();
                        } else if (xmlR.getLocalName().equals("NODO")) {
                            nodeToAdd = new Node(Integer.parseInt(xmlR.getAttributeValue(0)) - 1);
                        } else if (xmlR.getLocalName().equals("TIPO") && nodeToAdd != null) {
                            xmlR.next();
                            nodeToAdd.setType(NodeType.valueOf(xmlR.getText()));
                        } else if (xmlR.getLocalName().equals("COLLEGAMENTO") && nodeToAdd != null) {
                            xmlR.next();
                            nodeToAdd.addAdjacentNode(Integer.parseInt(xmlR.getText()) - 1);
                        }
                    }
                    case XMLStreamConstants.END_ELEMENT -> {
                        if (xmlR.getLocalName().equals("MAPPA") && map != null) {
                            values.add(map);
                        } else if (xmlR.getLocalName().equals("NODO") && nodeToAdd != null) {
                            map.add(nodeToAdd);
                        }
                    }
                }
                xmlR.next();
            }
        } catch (XMLStreamException | NoSuchElementException e) {
            System.out.println("Reading error:\n" + e.getMessage());
        }
        return values;
    }

    /**
     * read cities from xml
     * @param filename filename
     * @return list of cities
     */
    public static HashMap<String, String> readCities(String filename) {

        HashMap<String, String> cities = new HashMap<>();
        initializeXMLReader(filename);

        try {
            while (xmlR.hasNext()) {
                if (xmlR.getEventType() == XMLStreamConstants.CHARACTERS && xmlR.getText().trim().length() > 0) {
                    String cityName = xmlR.getText();
                    do {
                        xmlR.next();
                        if (xmlR.getEventType() == XMLStreamConstants.CHARACTERS && xmlR.getText().trim().length() > 0) {
                            cities.put(cityName, xmlR.getText());
                            break;
                        }
                    } while (xmlR.hasNext());
                }
                xmlR.next();
            }
        } catch (XMLStreamException | NoSuchElementException e) {
            System.out.println("Reading error:\n" + e.getMessage());
        }
        return cities;
    }

    /**
     * read people from Xml
     * @param filename filaname
     * @return list of people
     */
    public static ArrayList<Person> readPeople(String filename) {

        initializeXMLReader(filename);

        ArrayList<Person> people = new ArrayList<>();
        Person personToAdd = null;

        try {
            while (xmlR.hasNext()) {
                switch (xmlR.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        switch (xmlR.getLocalName()) {
                            case "persona" -> personToAdd = new Person();
                            case "nome" -> {
                                xmlR.next();
                                personToAdd.setName(xmlR.getText());
                            }
                            case "cognome" -> {
                                xmlR.next();
                                personToAdd.setSurname(xmlR.getText());
                            }
                            case "sesso" -> {
                                xmlR.next();
                                personToAdd.setSex(Sex.valueOf(xmlR.getText()));
                            }
                            case "comune_nascita" -> {
                                xmlR.next();
                                personToAdd.setBirthCity(xmlR.getText());
                            }
                            case "data_nascita" -> {
                                xmlR.next();
                                personToAdd.setBirthdate(xmlR.getText());
                            }
                            case "codice_fiscale" -> {
                                xmlR.next();
                                personToAdd.setTaxIdCode(new TaxIdCode(xmlR.getText()));
                            }
                            case "data_scadenza_id" -> {
                                xmlR.next();
                                personToAdd.setExpireDate(xmlR.getText());
                            }
                        }
                    }
                    case XMLStreamConstants.END_ELEMENT -> {
                        if (xmlR.getLocalName().equals("persona") && personToAdd != null) {
                            people.add(personToAdd);
                        }
                    }
                }
                xmlR.next();
            }
        } catch (XMLStreamException | NoSuchElementException e) {
            System.out.println("Reading error:\n" + e.getMessage());
        }
        return people;
    }
}
