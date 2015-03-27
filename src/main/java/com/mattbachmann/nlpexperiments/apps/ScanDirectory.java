package com.mattbachmann.nlpexperiments.apps;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mattbachmann.nlpexperiments.detectors.*;
import com.mattbachmann.nlpexperiments.extraction.ExtractionException;
import com.mattbachmann.nlpexperiments.extraction.TextExtractor;
import com.mattbachmann.nlpexperiments.modules.CTAKESModule;
import com.mattbachmann.nlpexperiments.modules.NLPModule;
import com.mattbachmann.nlpexperiments.utils.TextUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ScanDirectory {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        if(args.length != 1) {
            System.out.println("I only take one arg, which is the path of the directory to scan");
            return;
        }
        //Disable the model logging because its noisy and I dont care for right now
        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for ( Logger logger : loggers ) {
            logger.setLevel(Level.OFF);
        }
        System.out.println("Initializing models");
        Injector injector = Guice.createInjector(
                new NLPModule(),
                new CTAKESModule()
        );
        List<Detector> detectors = new ArrayList<>();
        detectors.add(injector.getInstance(LocationDetector.class));
        detectors.add(injector.getInstance(PersonDetector.class));
        detectors.add(injector.getInstance(MedicalTermDetector.class));

        String locationLabel = detectors.get(0).getLabel();
        String personLabel = detectors.get(1).getLabel();
        String medicalLabel = detectors.get(2).getLabel();

        System.out.println("Collecting files");
        Collection<File> files = FileUtils.listFiles(
                new File(args[0]),
                TrueFileFilter.INSTANCE,
                TrueFileFilter.INSTANCE
        );

        //After we got our files and the startup, start our timer
        System.out.println("Starting timer");
        Long startTime = System.currentTimeMillis();
        List<String> matches = new ArrayList<>();
        List<List<String>> csvResults = new ArrayList<>();
        System.out.println(String.format("Scanning %s files", files.size()));
        files.forEach(f -> {
            try {
                System.out.print(".");
                String textToScan = TextExtractor.extractText(f);
                Map<String, List<String>> results = TextUtils.extractMatchesFromText(detectors, textToScan);
                matches.add(
                        String.format(
                                "%s | %s : %s - %s : %s - %s - %s",
                                f.getAbsolutePath(),
                                personLabel, results.get(personLabel),
                                locationLabel, results.get(locationLabel),
                                medicalLabel, results.get(medicalLabel)
                        )
                );
                csvResults.add(
                        Arrays.asList(
                                f.getAbsolutePath(),
                                String.valueOf(results.get(personLabel).size()),
                                String.valueOf(results.get(locationLabel).size()),
                                String.valueOf(results.get(medicalLabel).size())
                        )
                );
            } catch (ExtractionException e) {
                System.out.println(
                        String.format("Failed to extract text from %s", f.getAbsolutePath())
                );
            } catch (DetectionException e) {
                System.out.println(
                        String.format("Failed to run detector on %s", f.getAbsolutePath())
                );
            }

        });
        System.out.println();
        Long endTime = System.currentTimeMillis();
        System.out.println(String.format("Time to run %s", (endTime - startTime)));
        System.out.println("Writing results");
        PrintWriter matchWriter = new PrintWriter("matches.txt", "UTF-8");
        matches.forEach(matchWriter::write);
        matchWriter.close();
        PrintWriter countWriter = new PrintWriter("counts.csv", "UTF-8");
        countWriter.write("\"Filename\",\"Location\",\"Person\",\"Medical\"\n");
        csvResults.forEach(csvResult -> countWriter.write(
                String.format(
                        "\"%s\",\"%s\",\"%s\",\"%s\"\n",
                        csvResult.get(0),
                        csvResult.get(1),
                        csvResult.get(2),
                        csvResult.get(3)
                )
        ));
        countWriter.close();
    }
}
