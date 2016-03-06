package nl.wexsol.xml.performance.compression;

import org.xml.sax.InputSource;

import java.io.OutputStream;

/**
 *  Simple interface to get perform (streaming) operations on XML documents
 */
public interface Compressor {

    /**
     *   Process an (XML) data stream
     * @param is    Document as inputstream
     * @param os    Compressed output stream
     * @throws Exception Something bad happened
     */
    void process(InputSource is, OutputStream os) throws Exception;

    /**
     *  Get name of conmpressor
     *
     * @return Name of Compressor
     */
    String getShortName();

    /**
     *  Get file extension of compressor
     *
     * @return Filename extension
     *
     */
    String getFilenameExtension();

}
