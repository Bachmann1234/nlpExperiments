package com.mattbachmann.nlpexperiments.utils;

import com.google.inject.Inject;
import com.mattbachmann.nlpexperiments.detectors.Detector;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.tokenize.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TextUtils {

    @Inject
    private static SentenceDetector sentenceDetector;

    @Inject
    private static Tokenizer tokenizer;

    /**
     * See the associated test for example input and output but roughly this will return an N
     * element list where N is the number of sentences in the text.
     *
     * @param text
     *  The text to tokenize
     * @return
     *  Returns a list of String arrays. The outer list is sentences and the inner arrays
     * are the tokens of that sentence.
     */
    public static List<String[]> tokenizeBySentence(String text) {
        List<String[]> tokens = new ArrayList<>();
        String sentences[] = sentenceDetector.sentDetect(text);
        for (String sentence : sentences) {
            tokens.add(tokenizer.tokenize(sentence));
        }
        return tokens;
    }

    public static Map<String, List<String>> extractMatchesFromText(List<Detector> detectors, String text) {
        Map<String, List<String>> results = new HashMap<>();
        List<String[]> tokens = tokenizeBySentence(text);
        detectors.forEach(detector -> results.put(detector.getLabel(), detector.findAll(tokens)));
        return results;
    }
}
