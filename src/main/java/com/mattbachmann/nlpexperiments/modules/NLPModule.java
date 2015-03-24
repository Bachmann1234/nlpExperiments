package com.mattbachmann.nlpexperiments.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.mattbachmann.nlpexperiments.utils.TextUtils;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.IOException;
import java.io.InputStream;

public class NLPModule extends AbstractModule {
    @Override
    protected void configure() {
        InputStream personModelFile;
        InputStream locationModelFile;
        InputStream sentenceModelFile;
        InputStream tokenModelFile;
        sentenceModelFile = this.getClass().getResourceAsStream("/models/en-sent.bin");
        personModelFile = this.getClass().getResourceAsStream("/models/en-ner-person.bin");
        locationModelFile = this.getClass().getResourceAsStream("/models/en-ner-location.bin");
        tokenModelFile = this.getClass().getResourceAsStream("/models/en-token.bin");

        SentenceDetector sentenceDetector;
        NameFinderME personModel;
        NameFinderME locationModel;
        Tokenizer tokenizer;
        try {
            personModel = new NameFinderME(new TokenNameFinderModel(personModelFile));
            locationModel = new NameFinderME(new TokenNameFinderModel(locationModelFile));
            tokenizer = new TokenizerME(new TokenizerModel(tokenModelFile));
            sentenceDetector = new SentenceDetectorME(new SentenceModel(sentenceModelFile));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create a models from model files", e);
        }
        bind(SentenceDetector.class).toInstance(sentenceDetector);
        bind(Tokenizer.class).toInstance(tokenizer);
        requestStaticInjection(TextUtils.class);

        bind(NameFinderME.class).annotatedWith(Names.named("personModel")).toInstance(personModel);
        bind(NameFinderME.class).annotatedWith(Names.named("locationModel")).toInstance(locationModel);
    }
}
