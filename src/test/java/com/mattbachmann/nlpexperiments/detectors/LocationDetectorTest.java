package com.mattbachmann.nlpexperiments.detectors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mattbachmann.nlpexperiments.modules.NLPModule;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LocationDetectorTest {

    private static Detector locationDetector;

    @BeforeClass
    public static void setUp() {
        Injector injector = Guice.createInjector(
                new NLPModule()
        );
        locationDetector = injector.getInstance(LocationDetector.class);
    }

    @Test
    public void testFindAll() throws Exception {

        List<String> names = locationDetector.findAll("Some people like Paris. Others Like Boston Massachusetts. The president lives at 1600 Pennsylvania Avenue Northwest, Washington, DC 20500");

        //I'm offended this model did not pick up Boston :-p
        assertEquals(
                Arrays.asList("Paris", "Massachusetts", "Pennsylvania Avenue Northwest", "Washington", "DC 20500"),
                names
        );
    }
}