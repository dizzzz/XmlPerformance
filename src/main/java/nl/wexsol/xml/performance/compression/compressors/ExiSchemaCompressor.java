package nl.wexsol.xml.performance.compression.compressors;

import com.siemens.ct.exi.GrammarFactory;
import com.siemens.ct.exi.exceptions.EXIException;
import nl.wexsol.xml.performance.compression.SingleSchema;

import java.nio.file.Path;

/**
 * Perform EXI compression operations on XML documents while applying an XML schema
 */
public class ExiSchemaCompressor extends ExiCompressor implements SingleSchema {
    @Override
    public void setSchema(Path xsd) {
        try {
            GrammarFactory gf = GrammarFactory.newInstance();
            grammars = gf.createGrammars(xsd.toString());

        } catch (EXIException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getShortName() {
        return "exi-schema";
    }
}
