package nl.wexsol.xml.performance.parse.stax;

import nl.wexsol.xml.performance.parse.stax.readers.EventReader;
import nl.wexsol.xml.performance.parse.stax.readers.StreamReader;

import javax.xml.stream.XMLInputFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 *  Measure performance of StAX XML parsers.
 */
public class Main {

    public static final int REPETITIONS = 100;

    public static void main(String[] args) {


        // javax.xml.stream.XMLInputFactory
        List<XMLInputFactory> factories = new ArrayList<>();

        // Add default factory
        System.setProperty("javax.xml.stream.XMLInputFactory", "com.sun.xml.internal.stream.XMLInputFactoryImpl");
        factories.add(XMLInputFactory.newFactory());

        // Load factories from classpath
        ServiceLoader serviceLoader =  ServiceLoader.load(javax.xml.stream.XMLInputFactory.class);
        serviceLoader.forEach(x -> factories.add((XMLInputFactory)x));

        // Setup data
        Path dataDir = Paths.get("data");
        Path xmlFile = dataDir.resolve("in.xml");

        Main mn = new Main();

        for(XMLInputFactory factory: factories){
            mn.process(xmlFile, factory);
        }


    }

    void process(Path xmlFile, XMLInputFactory factory){

        String factoryName = factory.getClass().getCanonicalName();

        System.out.println(factoryName);

        try {
            // construct readers
            StaxReader[]  readers = {new EventReader(factory), new StreamReader(factory)};

            // Iterate over readers
            for (StaxReader reader : readers) {

                // statistics
                List<Long> durations = new ArrayList<>();
                List<Long> nrElements = new ArrayList<>();


                for (int i = 0; i < REPETITIONS; i++) {

                    long begin = System.currentTimeMillis();

                    final long count = reader.countStartElements(new BufferedInputStream(Files.newInputStream(xmlFile)));
                    nrElements.add(count);

                    long end = System.currentTimeMillis();

                    durations.add((end-begin));

                }

                final long l = nrElements.stream().collect(Collectors.averagingLong(Long::longValue)).longValue();

                final LongSummaryStatistics statistics = durations.stream().collect(Collectors.summarizingLong(Long::longValue));


                System.out.printf("%s: #=%s  avg=%s ms  max=%s ms  min=%s ms%n", reader.getName(), l, statistics.getAverage(), statistics.getMax(), statistics.getMin());

            }

        } catch(IOException ex) {
            ex.printStackTrace();
        }

        System.out.println();

    }
}
