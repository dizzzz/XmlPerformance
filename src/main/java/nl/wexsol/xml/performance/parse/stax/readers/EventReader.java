package nl.wexsol.xml.performance.parse.stax.readers;

import nl.wexsol.xml.performance.parse.stax.StaxReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

/**
 * Reader that uses the StAX XMLEventReader to parse an XML document.
 */
public class EventReader implements StaxReader {

    private final XMLInputFactory factory;


    public EventReader() {
        this.factory = XMLInputFactory.newInstance();
    }


    public EventReader(XMLInputFactory factory) {
        this.factory=factory;
    }

    @Override
    public long countStartElements(InputStream is) {
        long counter = 0;
        long sum = 0;

        try {
            XMLEventReader eventReader = factory.createXMLEventReader(is);

            while (eventReader.hasNext()) {

                XMLEvent xmlEvent = eventReader.nextEvent();

                if (xmlEvent.isStartElement()) {
                    counter++;
                    sum += xmlEvent.asStartElement().getName().getLocalPart().length();

                }

            }

            eventReader.close();

        } catch (XMLStreamException e) {
            System.err.println(sum);
            e.printStackTrace();
            counter = -1;
        }

        return counter;
    }

    @Override
    public String getName() {
        return "EventReader";
    }
}
