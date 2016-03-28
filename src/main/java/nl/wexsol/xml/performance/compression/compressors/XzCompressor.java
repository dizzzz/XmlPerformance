package nl.wexsol.xml.performance.compression.compressors;

import nl.wexsol.xml.performance.compression.Compressor;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.io.IOUtils;
import org.xml.sax.InputSource;

import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Perform XZ compression operations on (XML) documents
 */
public class XzCompressor implements Compressor {
    @Override
    public void process(InputSource is, OutputStream os) throws Exception {

        XZCompressorOutputStream xzos = new XZCompressorOutputStream(os);
        IOUtils.copy(is.getByteStream(), xzos);
        xzos.flush();
        xzos.finish();
    }

    @Override
    public String getShortName() {
        return "xz";
    }

    @Override
    public String getFilenameExtension() {
        return ".xz";
    }
}
