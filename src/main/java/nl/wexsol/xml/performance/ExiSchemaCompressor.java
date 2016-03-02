package nl.wexsol.xml.performance;

import com.siemens.ct.exi.GrammarFactory;
import com.siemens.ct.exi.exceptions.EXIException;

import java.nio.file.Path;

/**
 * Created by wessels on 1/3/16.
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
