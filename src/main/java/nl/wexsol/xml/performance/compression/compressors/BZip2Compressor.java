package nl.wexsol.xml.performance.compression.compressors;

import nl.wexsol.xml.performance.compression.Compressor;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.io.IOUtils;
import org.xml.sax.InputSource;

import java.io.OutputStream;

/**
 * Perform XZ compression operations on (XML) documents
 */
public class BZip2Compressor implements Compressor {
    @Override
    public void process(InputSource is, OutputStream os) throws Exception {

        BZip2CompressorOutputStream bzos = new BZip2CompressorOutputStream(os);
        IOUtils.copy(is.getByteStream(), bzos);
        bzos.finish();
        bzos.flush();
    }

    @Override
    public String getShortName() {
        return "bzip2";
    }

    @Override
    public String getFilenameExtension() {
        return ".bz2";
    }
}
