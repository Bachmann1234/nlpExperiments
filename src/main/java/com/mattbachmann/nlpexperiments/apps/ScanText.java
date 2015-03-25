package com.mattbachmann.nlpexperiments.apps;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mattbachmann.nlpexperiments.detectors.DetectionException;
import com.mattbachmann.nlpexperiments.detectors.Detector;
import com.mattbachmann.nlpexperiments.detectors.LocationDetector;
import com.mattbachmann.nlpexperiments.detectors.PersonDetector;
import com.mattbachmann.nlpexperiments.modules.NLPModule;
import com.mattbachmann.nlpexperiments.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class ScanText {
    public static void main(String[] args) throws DetectionException {
        Injector injector = Guice.createInjector(
                new NLPModule()
        );
        List<Detector> detectors = new ArrayList<>();
        detectors.add(injector.getInstance(LocationDetector.class));
        detectors.add(injector.getInstance(PersonDetector.class));

        for(String arg : args) {
            System.out.println(String.format("Processing Text:\n%s", arg));
            TextUtils.extractMatchesFromText(detectors, arg).forEach(
                    (key, value) -> System.out.println(String.format("%s detector found: %s", key, value))
            );
            System.out.println();
        }

    }
}
