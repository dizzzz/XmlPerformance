package nl.wexsol.xml.performance.parse.stax.readers;

import nl.wexsol.xml.performance.parse.stax.StaxReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

/**
 * Reader that uses the StAX XMLStreamReader to parse an XML document.
 */
public class StreamReader implements StaxReader {

    private final XMLInputFactory factory;


    public StreamReader() {
        this.factory = XMLInputFactory.newInstance();
    }


    public StreamReader(XMLInputFactory factory) {
        this.factory=factory;
    }


    @Override
    public long countStartElements(InputStream is) {
        long counter = 0;
        long sum = 0;

        try {

            XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(is);

            while (xmlStreamReader.hasNext()) {

                int next = xmlStreamReader.next();

                if (next == XMLStreamConstants.START_ELEMENT) {
                    counter++;
                    sum += xmlStreamReader.getLocalName().length();
                }

            }

            xmlStreamReader.close();


        } catch (XMLStreamException e) {
            System.out.println(sum);
            e.printStackTrace();
            counter = -1;
        }


        return counter;
    }

    @Override
    public String getName() {
        return "StreamReader";
    }
}
