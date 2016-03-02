package nl.wexsol.xml.performance;

import org.apache.commons.io.IOUtils;
import org.xml.sax.InputSource;

import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by wessels on 29/2/16.
 */
public class GzipCompressor implements Compressor {
    @Override
    public void process(InputSource is, OutputStream os) throws Exception {
        GZIPOutputStream gos = new GZIPOutputStream(os);
        IOUtils.copy(is.getByteStream(), gos);
        gos.finish();

    }

    @Override
    public String getShortName() {
        return "gzip";
    }
}
