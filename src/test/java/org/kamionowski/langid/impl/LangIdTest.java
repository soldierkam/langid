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
        DefaultModelReader reader = new DefaultModelReader();
        LangId langId = new LangIdImpl(reader, "en", "pl");
        Assert.assertArrayEquals(new String[]{"pl", "en"}, langId.knownLanguages().toArray(new String[2]));
        Assert.assertEquals(449, reader.knownLangIds().size());
    }

    @Test
    public void testCreateTrigrams() throws IOException {
        LangIdImpl langId = new LangIdImpl("en");
        String[] trigramsEN = langId.split("split some words and some short w o r d s");
        Assert.assertArrayEquals(new String[]{"<sp", "it>", "spl", "pli", "lit",
                "<so", "me>", "som", "ome",
                "<wo", "ds>", "wor", "ord", "rds",
                "<an", "nd>", "and",
                "<so", "me>", "som", "ome",
                "<sh", "rt>", "sho", "hor", "ort"}, trigramsEN);

        String[] trigramsPL = langId.split("Jesteśmy zbyt tchórzliwym...narodem");
        Assert.assertArrayEquals(new String[]{"<je", "my>", "jes", "est", "ste", "teś", "eśm", "śmy",
                "<zb", "yt>", "zby", "byt",
                "<tc", "ym>", "tch", "chó", "hór", "órz", "rzl", "zli", "liw", "iwy", "wym",
                "<na", "em>", "nar", "aro", "rod", "ode", "dem"}, trigramsPL);

    }


    @Test
    public void testDetect() throws IOException {
        LangIdImpl langId = new LangIdImpl("en", "de", "ru", "pl");
        Assert.assertEquals("en", langId.detect("Split some word and detect language").bestMatch());
        Assert.assertEquals("de", langId.detect("Wir sind Menschen zu feige, um zu gehen und endlich ehrlich sagen, dass ich nicht weiß, wie man Fußball spielen").bestMatch());
        Assert.assertEquals("ru", langId.detect("В сирийской оппозиции наметился раскол - представитель оппозиционного движения Камаль Аль-Лабвани (ВИДЕО)").bestMatch());
        Assert.assertEquals("pl", langId.detect("Jesteśmy zbyt tchórzliwym narodem, żeby sobie wreszcie szczerze powiedzieć, że nie umiemy grać w piłkę").bestMatch());
    }
}
