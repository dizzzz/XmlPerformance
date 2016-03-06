package nl.wexsol.xml.performance.parse.stax;

import java.io.InputStream;

/**
 *  Simple interface to get statistics data from an XML document
 */
public interface StaxReader {

    /**
     *  Count nr of Start elements in XML stream
     *
     * @param is Inputstream
     * @return  Number of start elements
     */
    long countStartElements(InputStream is);

    /**
     *  Get name of reader, eg for reporting purposes.
     * @return Name of reader.
     */
    String getName();

}
