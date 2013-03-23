package org.kamionowski.langid.impl;

import org.kamionowski.langid.LangResults;
import org.kamionowski.langid.Mark;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: soldier
 * Date: 23.03.13
 * Time: 13:36
 */
class LangResultsImpl implements LangResults {

    private final List<? extends Mark> marks;

    public LangResultsImpl(final DoubleZeroMap<String> lang2Score) {
        List<MarkImpl> tmpMarks = new LinkedList<MarkImpl>();
        for (Map.Entry<String, Double> score : lang2Score.entrySet()) {
            tmpMarks.add(new MarkImpl(score.getKey(), score.getValue()));
        }
        Collections.sort(tmpMarks);
        marks = Collections.unmodifiableList(tmpMarks);
    }

    @Override
    public String bestMatch() {
        return marks.get(0).lang();
    }

    @Override
    public List<? extends Mark> sortedResults() {
        return marks;
    }
}
