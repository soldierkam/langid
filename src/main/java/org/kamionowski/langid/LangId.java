package org.kamionowski.langid;

import java.util.List;

/**
 * User: soldier
 * Date: 22.03.13
 * Time: 23:47
 */
public interface LangId {

    LangResults detect(String text);

    List<String> knownLanguages();
}
