package nl.wexsol.xml.performance;

import com.siemens.ct.exi.CodingMode;
import com.siemens.ct.exi.EXIFactory;
import com.siemens.ct.exi.GrammarFactory;
import com.siemens.ct.exi.api.sax.EXIResult;
import com.siemens.ct.exi.exceptions.EXIException;
import com.siemens.ct.exi.helpers.DefaultEXIFactory;
import com.sun.tools.classfile.Type;
import com.sun.xml.fastinfoset.sax.SAXDocumentSerializer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.io.output.NullOutputStream;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

/**
 * Created by wessels on 15/2/16.
 */
public class XmlCompressor {

    public static final String IN_XML = "in2.xml";


    public static void main(String[] args) {

        try {
            System.out.println(FileUtils.byteCountToDisplaySize(Files.size(Paths.get(IN_XML))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Compressor comps[] = {new GzipCompressor(), new ExiCompressor(), new ExiSchemaCompressor(), new FastInfosetCompressor()};
        Arrays.asList(comps).stream().filter(x -> (x instanceof SingleSchema)).forEach(xc -> ((SingleSchema) xc).setSchema(Paths.get("ADELler_v2.5.1.xml")));



        for (Compressor xc : comps) {

            try (InputStream inputStream = Files.newInputStream(Paths.get(IN_XML));
                 CountingOutputStream os = new CountingOutputStream(new NullOutputStream())) {


                xc.process(new InputSource(inputStream), os);

                os.flush();
                System.out.println(xc.getShortName() + "=" + FileUtils.byteCountToDisplaySize(os.getByteCount()));


            } catch (Throwable t) {
                t.printStackTrace();
            }

        }

        for (Compressor xc : comps) {

            List<Long> values = new ArrayList<>();

            for(int i=0 ; i<10 ; i++) {
                try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(IN_XML)));
                     OutputStream os = new NullOutputStream()) {

                    long begin = System.currentTimeMillis();

                    xc.process(new InputSource(inputStream), os);
                    os.flush();

                    long end = System.currentTimeMillis();

                    values.add(end-begin);

                } catch (Throwable t) {
                    t.printStackTrace();

                }

            }

            LongSummaryStatistics collect = values.stream().collect(Collectors.summarizingLong(Long::longValue));

            System.out.println(xc.getShortName() + "=" + collect.toString());
        }


    }

}

