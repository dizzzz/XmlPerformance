package nl.wexsol.xml.performance;

import com.siemens.ct.exi.CodingMode;
import com.siemens.ct.exi.EXIFactory;
import com.siemens.ct.exi.GrammarFactory;
import com.siemens.ct.exi.api.sax.EXIResult;
import com.siemens.ct.exi.exceptions.EXIException;
import com.siemens.ct.exi.grammars.Grammars;
import com.siemens.ct.exi.helpers.DefaultEXIFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.OutputStream;
import java.nio.file.Path;

/**
 * Created by wessels on 29/2/16.
 */
public class ExiCompressor implements Compressor {


    protected Grammars grammars;


    @Override
    public void process(InputSource is, OutputStream os) throws Exception {
        EXIFactory exiFactory = DefaultEXIFactory.newInstance();
        exiFactory.setCodingMode(CodingMode.COMPRESSION);

        if (grammars != null) {
            exiFactory.setGrammars(grammars);
        }

        EXIResult exiResult = new EXIResult(exiFactory);
        exiResult.setOutputStream(os);
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        xmlReader.setContentHandler(exiResult.getHandler());
        xmlReader.parse(is);

        os.flush();
    }

    @Override
    public String getShortName() {
        return "exi";
    }

    @Override
    public String getFilenameExtension() {
        return ".exi";
    }


}

