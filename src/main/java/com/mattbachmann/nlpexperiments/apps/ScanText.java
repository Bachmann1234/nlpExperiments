package com.mattbachmann.nlpexperiments.apps;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mattbachmann.nlpexperiments.detectors.*;
import com.mattbachmann.nlpexperiments.modules.DrugModule;
import com.mattbachmann.nlpexperiments.modules.NLPModule;
import com.mattbachmann.nlpexperiments.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class ScanText {
    public static void main(String[] args) throws DetectionException {
        Injector injector = Guice.createInjector(
                new NLPModule(),
                new DrugModule()
        );
        List<Detector> detectors = new ArrayList<>();
        detectors.add(injector.getInstance(LocationDetector.class));
        detectors.add(injector.getInstance(PersonDetector.class));
        detectors.add(injector.getInstance(DrugDetector.class));

        for(String arg : args) {
            System.out.println(String.format("Processing Text:\n%s", arg));
            TextUtils.extractMatchesFromText(detectors, arg).forEach(
                    (key, value) -> System.out.println(String.format("%s detector found: %s", key, value))
            );
            System.out.println();
        }

    }
}
