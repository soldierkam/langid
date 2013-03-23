package org.kamionowski.langid.impl;

import org.kamionowski.langid.Model;
import org.kamionowski.langid.ModelReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: soldier
 * Date: 22.03.13
 * Time: 23:36
 */
public class DefaultModelReader implements ModelReader {

    private final static Pattern FILENAME_PATTERN = Pattern.compile("([a-z]{1,3})\\-3grams\\.txt");

    @Override
    public Model read(String langId) throws IOException {
        return new ModelImpl(this.getClass().getResourceAsStream(langId + "-3grams.txt"));
    }

    @Override
    public Set<String> knownLangIds() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String pattern = "classpath*:" + this.getClass().getPackage().getName().replace(".", "/") + "/*-3grams.txt";
        Resource resources[] = resolver.getResources(pattern);
        Set results = new HashSet();
        for (Resource res : resources) {
            Matcher matcher = FILENAME_PATTERN.matcher(res.getFilename());
            if (!matcher.matches()) {
                throw new RuntimeException("Cannot match: " + res.getFilename());
            }
            results.add(matcher.group(1));
        }
        return results;
    }
}
