package nl.wexsol.xml.performance.compression.compressors;

import nl.wexsol.xml.performance.compression.Compressor;
import org.apache.commons.io.IOUtils;
import org.xml.sax.InputSource;

import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Perform GZIP compression operations on (XML) documents
 */
public class GzipCompressor implements Compressor {
    @Override
    public void process(InputSource is, OutputStream os) throws Exception {
        GZIPOutputStream gos = new GZIPOutputStream(os);
        IOUtils.copy(is.getByteStream(), gos);
        gos.finish();
        gos.flush();
    }

    @Override
    public String getShortName() {
        return "gzip";
    }

    @Override
    public String getFilenameExtension() {
        return ".gz";
    }
}
