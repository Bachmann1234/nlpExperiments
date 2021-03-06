package com.mattbachmann.nlpexperiments.detectors;

import com.mattbachmann.nlpexperiments.utils.TextUtils;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.util.Span;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractNameDetector implements Detector {
    private final NameFinderME nameFinder;

    protected AbstractNameDetector(NameFinderME nameFinder) {
        this.nameFinder = nameFinder;
    }

    public List<String> findAll(String text) {
        return this.findAll(TextUtils.tokenizeBySentence(text));
    }

    public List<String> findAll(List<String[]> tokensBySentence) {
        List<String> results = new ArrayList<>();

        tokensBySentence.forEach(tokens -> {
            Span[] nameSpans = nameFinder.find(tokens);
            String[] names = Span.spansToStrings(nameSpans, tokens);
            results.addAll(Arrays.asList(names));
        });
        nameFinder.clearAdaptiveData();

        return results;
    }
}
