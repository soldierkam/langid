package org.kamionowski.langid;

/**
 * User: soldier
 * Date: 22.03.13
 * Time: 23:35
 */
public interface Model {
    int freq(String trigram);

    int count();
}
