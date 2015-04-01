package com.mattbachmann.nlpexperiments.detectors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mattbachmann.nlpexperiments.modules.DrugModule;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DrugDetectorTest {
    private static Detector drugDetector;

    @BeforeClass
    public static void setUp() {
        Injector injector = Guice.createInjector(
                new DrugModule()
        );
        drugDetector = injector.getInstance(DrugDetector.class);
    }

    @Test
    public void testMedicalDetector() throws Exception {
        List<String> expectedResults = Arrays.<String>asList(
                "aspirin",
                "Today"
        );

        List<String> results = drugDetector.findAll("Dr. Nutritious\n" +
                " \n" +
                "Medical Nutrition Therapy for Hyperlipidemia\n" +
                "Referral from: Julie Tester, RD, LD, CNSD\n" +
                "Phone contact: (555) 555-1212\n" +
                "Height: 144 cm Current Weight: 45 kg Date of current weight: 02-29-2001\n" +
                "Admit Weight: 53 kg BMI: 18 kg/m2\n" +
                "Diet: General\n" +
                "Daily Calorie needs (kcals): 1500 calories, assessed as HB + 20% for activity.\n" +
                "Daily Protein needs: 40 grams, assessed as 1.0 g/kg.\n" +
                "Pt has been on a 3-day calorie count and has had an average intake of 1100 calories.\n" +
                "She was instructed to drink 2-3 cans of liquid supplement to help promote weight gain.\n" +
                "She agrees with the plan and has my number for further assessment. May want a Resting\n" +
                "Metabolic Rate as well. She takes an aspirin a day for knee pain. Today Active");

        assertEquals(expectedResults, results);
    }

}