package org.kamionowski.langid;

import java.util.List;

/**
 * User: soldier
 * Date: 23.03.13
 * Time: 13:34
 */
public interface LangResults {

    String bestMatch();

    List<? extends Mark> sortedResults();
}
