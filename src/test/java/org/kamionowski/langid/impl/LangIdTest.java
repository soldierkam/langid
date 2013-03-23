package org.kamionowski.langid.impl;

import org.junit.Assert;
import org.junit.Test;
import org.kamionowski.langid.LangId;

import java.io.IOException;

/**
 * User: soldier
 * Date: 22.03.13
 * Time: 23:51
 */
public class LangIdTest {

    @Test
    public void testRead() throws IOException {
        LangId langId = new LangIdImpl("en", "pl");
    }

    @Test
    public void testParser() throws IOException {
        LangIdImpl langId = new LangIdImpl("en");
        String[] trigrams = langId.split("split some words and some short w o r d s");
        Assert.assertArrayEquals(new String[]{"spl", "pli", "lit", "som", "ome", "wor", "ord", "rds", "and", "som", "ome", "sho", "hor", "ort"}, trigrams);
    }


    @Test
    public void testDetect() throws IOException {
        LangIdImpl langId = new LangIdImpl("en", "de", "ru", "pl");
        Assert.assertEquals("en", langId.detect("Split some word and detect language"));
        Assert.assertEquals("de", langId.detect("Wir sind Menschen zu feige, um zu gehen und endlich ehrlich sagen, dass ich nicht weiß, wie man Fußball spielen"));
        Assert.assertEquals("ru", langId.detect("В сирийской оппозиции наметился раскол - представитель оппозиционного движения Камаль Аль-Лабвани (ВИДЕО)"));
        Assert.assertEquals("pl", langId.detect("Jesteśmy zbyt tchórzliwym narodem, żeby sobie wreszcie szczerze powiedzieć, że nie umiemy grać w piłkę"));
    }
}
