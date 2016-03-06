package nl.wexsol.xml.performance.compression;

import java.nio.file.Path;

/**
 *  Set a specific XML schema to use during processing.
 */
public interface SingleSchema {

    void setSchema(Path xsd);
}
