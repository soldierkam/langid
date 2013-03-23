package org.kamionowski.langid.impl;

import org.kamionowski.langid.LangId;
import org.kamionowski.langid.Model;
import org.kamionowski.langid.ModelReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: soldier
 * Date: 22.03.13
 * Time: 23:48
 */
public class LangIdImpl implements LangId {

    private Map<String, Model> models = new HashMap<String, Model>();

    public LangIdImpl(String... languages) throws IOException {
        ModelReader reader = new DefaultModelReader();
        for (String lang : languages) {
            models.put(lang, reader.read(lang));
        }
    }

    @Override
    public String detect(String text) {
        final String[] trigrams = split(text);
        final IntegerZeroMap<String> trigramFreq = new IntegerZeroMap<String>();
        for (String trigram : trigrams) {
            trigramFreq.inc(trigram);
        }
        final DoubleZeroMap<String> lang2Score = new DoubleZeroMap(models.keySet());
        final double trigramTotal = trigramFreq.valuesSum();
        for (Map.Entry<String, Integer> trigramWithFreq : trigramFreq.entrySet()) {
            final String trigram = trigramWithFreq.getKey();
            final double trigramCount = trigramWithFreq.getValue();
            for (Map.Entry<String, Model> lang2model : models.entrySet()) {
                Model model = lang2model.getValue();
                double step = ((double) model.freq(trigram) / (double) model.count()) * (trigramCount / trigramTotal);
                lang2Score.inc(lang2model.getKey(), step);
            }
        }
        double maxScore = -1;
        String lang = null;
        for (Map.Entry<String, Double> score : lang2Score.entrySet()) {
            if (score.getValue() >= maxScore) {
                maxScore = score.getValue();
                lang = score.getKey();
            }
        }
        return lang;
    }

    protected String[] split(String text) {
        String[] words = words(text.toLowerCase());
        return trigrams(words);
    }

    protected String[] words(String text) {
        return text.split(" ");
    }

    protected String[] trigrams(String[] words) {
        int trigramsCount = 0;
        for (String word : words) {
            trigramsCount += Math.max(word.length() - 2, 0);
        }
        String[] trigrams = new String[trigramsCount];
        int trigramIdx = 0;
        for (String word : words) {
            int wordLen = word.length();
            for (int i = 0; i < wordLen - 2; i++) {
                trigrams[trigramIdx++] = word.substring(i, i + 3);
            }
        }
        return trigrams;

    }
}
