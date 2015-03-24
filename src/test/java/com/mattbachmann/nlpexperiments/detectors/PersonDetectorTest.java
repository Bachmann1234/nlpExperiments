package com.mattbachmann.nlpexperiments.detectors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mattbachmann.nlpexperiments.modules.NLPModule;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonDetectorTest {

    private static Detector personDetector;

    @BeforeClass
    public static void setUp() {
        Injector injector = Guice.createInjector(
                new NLPModule()
        );
        personDetector = injector.getInstance(PersonDetector.class);
    }

    @Test
    public void testFindAll() throws Exception {

        List<String> names = personDetector.findAll("My name is Matthew Bachmann. Bill Gates founded Microsoft, Steve Jobs founded Apple with Steve Wozniak");

        assertEquals(
                Arrays.asList("Matthew Bachmann", "Bill Gates", "Steve Jobs", "Steve Wozniak"),
                names
        );
    }
}