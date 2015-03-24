package com.mattbachmann.nlpexperiments.apps;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mattbachmann.nlpexperiments.detectors.Detector;
import com.mattbachmann.nlpexperiments.detectors.LocationDetector;
import com.mattbachmann.nlpexperiments.detectors.PersonDetector;
import com.mattbachmann.nlpexperiments.modules.NLPModule;
import com.mattbachmann.nlpexperiments.utils.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScanText {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new NLPModule()
        );
        List<Detector> detectors = new ArrayList<>();
        detectors.add(injector.getInstance(LocationDetector.class));
        detectors.add(injector.getInstance(PersonDetector.class));

        Arrays.asList(args).forEach(textBlock -> {
            System.out.println(String.format("Processing Text:\n%s", textBlock));
            TextUtils.extractMatchesFromText(detectors, textBlock).forEach(
                    (key, value) -> System.out.println(String.format("%s detector found: %s", key, value))
            );
            System.out.println();
        });

    }
}
