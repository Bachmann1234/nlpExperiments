package com.mattbachmann.nlpexperiments.utils;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mattbachmann.nlpexperiments.detectors.Detector;
import com.mattbachmann.nlpexperiments.detectors.LocationDetector;
import com.mattbachmann.nlpexperiments.detectors.PersonDetector;
import com.mattbachmann.nlpexperiments.modules.NLPModule;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TextUtilsTest {

    private static List<Detector> detectors;

    @BeforeClass
    public static void setUp() {
        Injector injector = Guice.createInjector(
                new NLPModule()
        );
        detectors = new ArrayList<>();
        detectors.add(injector.getInstance(LocationDetector.class));
        detectors.add(injector.getInstance(PersonDetector.class));
    }

    @Test
    public void testTokenizeBySentence() throws Exception {
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[] {"I", "am", "a", "sentence", "."});
        expected.add(new String[] {"I", "contain", "words", "."});
        expected.add(new String[] {"I", "also", ",", "have", "more", "words", "."});
        List<String[]> result = TextUtils.tokenizeBySentence(
                "I am a sentence. I contain words. I also, have more words."
        );
        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(Arrays.asList(expected.get(i)), Arrays.asList(result.get(i)));
        }
    }

    @Test
    public void testExtractMatchesFromText() throws Exception {
        Map<String, List<String>> expectedResults = new HashMap<>();
        expectedResults.put(detectors.get(0).getLabel(), Arrays.asList("Massachusetts"));
        expectedResults.put(detectors.get(1).getLabel(), Arrays.asList("Matthew Bachmann"));
        Map<String, List<String>> results = TextUtils.extractMatchesFromText(
                detectors,
                "My name is Matthew Bachmann and I live in Massachusetts"
        );
        assertEquals(expectedResults, results);
    }
}