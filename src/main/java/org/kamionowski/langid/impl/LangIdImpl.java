package org.kamionowski.langid.impl;

import org.kamionowski.langid.LangId;
import org.kamionowski.langid.LangResults;
import org.kamionowski.langid.Model;
import org.kamionowski.langid.ModelReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: soldier
 * Date: 22.03.13
 * Time: 23:48
 */
public class LangIdImpl implements LangId {

    private Map<String, Model> models = new HashMap<String, Model>();

    public LangIdImpl(String... languages) throws IOException {
        this(new DefaultModelReader(), languages);
    }

    public LangIdImpl(ModelReader reader, String... languages) throws IOException {
        for (String langId : languages) {
            models.put(langId, reader.read(langId));
        }
    }

    @Override
    public List<String> knownLanguages() {
        return new LinkedList<String>(models.keySet());
    }

    @Override
    public LangResults detect(String text) {
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
        return new LangResultsImpl(lang2Score);
    }

    protected String[] split(String text) {
        String[] words = words(text.toLowerCase());
        return trigrams(words);
    }

    protected String[] words(String text) {
        text = text + " ";
        List<String> tmpWords = new LinkedList<String>();
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(c);
            } else if (sb.length() > 0) {
                tmpWords.add(sb.toString());
                sb = new StringBuilder(20);
            }
        }
        return tmpWords.toArray(new String[tmpWords.size()]);
    }

    protected String[] trigrams(String[] words) {
        int trigramsCount = 0;
        for (String word : words) {
            trigramsCount += Math.max(word.length() - 2, 0);
            trigramsCount += word.length() >= 2 ? 2 : 0;
        }
        String[] trigrams = new String[trigramsCount];
        int trigramIdx = 0;
        for (String word : words) {
            int wordLen = word.length();
            if (wordLen >= 2) {
                trigrams[trigramIdx++] = "<" + word.substring(0, 2);
                trigrams[trigramIdx++] = word.substring(wordLen - 2, wordLen) + ">";
            }
            for (int i = 0; i < wordLen - 2; i++) {
                trigrams[trigramIdx++] = word.substring(i, i + 3);
            }
        }
        return trigrams;

    }
}
