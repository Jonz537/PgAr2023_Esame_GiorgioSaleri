
import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class XmlUtils {

    private static XMLInputFactory xmlIf = null;
    private static XMLStreamReader xmlR = null;
    private static XMLOutputFactory xmlOf = null;
    private static XMLStreamWriter xmlW = null;

    private static void initializeXMLReader(String filename) {
        try {
            xmlIf = XMLInputFactory.newInstance();
            xmlR = xmlIf.createXMLStreamReader(filename, new FileInputStream(filename));
        } catch (Exception e) {
            System.out.println("Error in initializing XML stream reader:\n" + e.getMessage());
        }
    }

    public static ArrayList<Integer> readMap(String filename) {

        initializeXMLReader(filename);
        ArrayList<Integer> values = new ArrayList<>();

        try {
            int  readValue = 0;
            while (xmlR.hasNext()) {
                switch (xmlR.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        if (xmlR.getLocalName().equals("elementName")) {

                        } else if (xmlR.getLocalName().equals("otherElementName")) {

                        }
                    }
                    case XMLStreamConstants.END_ELEMENT -> {

                    }
                    case XMLStreamConstants.START_DOCUMENT -> {

                    }
                    case XMLStreamConstants.END_DOCUMENT -> {

                    }
                }
                xmlR.next();
            }
        } catch (XMLStreamException | NoSuchElementException e) {
            System.out.println("Reading error:\n" + e.getMessage());
        }
        return values;
    }

    private static void initializeWriterFileXml(String filename) {
        try {
            xmlOf = XMLOutputFactory.newInstance();
            xmlW = xmlOf.createXMLStreamWriter(new FileOutputStream(filename), "utf-8");
            xmlW.writeStartDocument("utf-8", "1.0");
        } catch (Exception e) {
            System.out.println("Error\n" + e.getMessage());
            System.out.println("Reading error:\n" + e.getMessage());
        }
    }

    public static void writeMap(ArrayList<String> data) {

        String filename = "./Routes.xml";

        initializeWriterFileXml(filename);

        try {
            xmlW.writeStartDocument();
            xmlW.writeStartElement("routes");

            xmlW.writeStartElement("nameElement");

            xmlW.writeAttribute("attributeName", "attributeValue");

            xmlW.writeEndElement(); // route

            xmlW.writeEndDocument();

            xmlW.flush();
            xmlW.close();

        } catch (XMLStreamException | NoSuchElementException e) {
            System.out.println("Reading error:\n" + e.getMessage());
        }
    }
}
