package nl.wexsol.xml.performance.compression;

import nl.wexsol.xml.performance.compression.compressors.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.io.output.NullOutputStream;
import org.xml.sax.InputSource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;

/**
 *  Measure performance of XML compressors
 */
public class Main {

    public static final String IN_XML = "in.xml";
    public static final int REPETITIONS = 10;


    public static void main(String[] args) {

        Path dataDir = Paths.get("data");
        Path xmlFile = dataDir.resolve("in.xml");

        try {
            System.out.println("Original size: " + FileUtils.byteCountToDisplaySize(Files.size(Paths.get("data", IN_XML))));
            System.out.println("Repetitions: " + REPETITIONS  );
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Compressor compressors[] = {new GzipCompressor(), new XzCompressor(), new BZip2Compressor(),  new ExiCompressor(), new ExiSchemaCompressor(), new FastInfosetCompressor()};
        Arrays.asList(compressors).stream().filter(x -> (x instanceof SingleSchema)).forEach(xc -> ((SingleSchema) xc).setSchema(dataDir.resolve("in.xsd")));


        System.out.println("Compressed sizes:");

        for (Compressor c : compressors) {

            Path out = dataDir.resolve(c.getShortName() + "_" + IN_XML + c.getFilenameExtension());

            try (InputStream inputStream = Files.newInputStream(xmlFile);
                 CountingOutputStream os = new CountingOutputStream(new BufferedOutputStream(Files.newOutputStream(out)))) {

                c.process(new InputSource(inputStream), os);

                System.out.println(c.getShortName() + "=" + FileUtils.byteCountToDisplaySize(os.getByteCount()));

            } catch (Throwable t) {
                t.printStackTrace();
            }

        }

        System.out.println();
        System.out.println("Timing: ");

        for (Compressor c : compressors) {

            List<Long> values = new ArrayList<>();

            for(int i = 0; i< REPETITIONS; i++) {
                try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(xmlFile));
                     OutputStream os = new NullOutputStream()) {

                    long begin = System.currentTimeMillis();

                    c.process(new InputSource(inputStream), os);

                    long end = System.currentTimeMillis();

                    values.add(end-begin);

                } catch (Throwable t) {
                    t.printStackTrace();

                }

            }

            LongSummaryStatistics statistics = values.stream().collect(Collectors.summarizingLong(Long::longValue));

            System.out.printf("%s: avg=%s ms  max=%s ms  min=%s ms%n",
                    c.getShortName(),  statistics.getAverage(), statistics.getMax(), statistics.getMin());

        }


    }

}

