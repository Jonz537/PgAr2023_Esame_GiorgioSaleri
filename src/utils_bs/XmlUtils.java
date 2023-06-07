package utils_bs;

import world_stuff.Node;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
}
