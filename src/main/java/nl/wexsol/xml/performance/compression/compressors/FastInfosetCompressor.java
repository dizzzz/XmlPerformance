package nl.wexsol.xml.performance.compression.compressors;

import com.sun.xml.fastinfoset.sax.SAXDocumentSerializer;
import nl.wexsol.xml.performance.compression.Compressor;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.OutputStream;

/**
 * Perform FastInfoSet compression operations on XML documents
 */
public class FastInfosetCompressor implements Compressor {
    @Override
    public void process(InputSource is, OutputStream os) throws Exception {
        SAXDocumentSerializer saxDocumentSerializer = new SAXDocumentSerializer();
        saxDocumentSerializer.setOutputStream(os);

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.setProperty("http://xml.org/sax/properties/lexical-handler", saxDocumentSerializer);
        saxParser.parse(is, saxDocumentSerializer);

        os.flush();
    }

    @Override
    public String getShortName() {
        return "fastinfoset";
    }

    @Override
    public String getFilenameExtension() {
        return ".fi";
    }
}
