package nl.wexsol.xml.performance;

import org.xml.sax.InputSource;

import java.io.OutputStream;

/**
 * Created by wessels on 29/2/16.
 */
public interface Compressor {

    void process(InputSource is, OutputStream os) throws Exception;

    String getShortName();

}
