package org.kamionowski.langid.impl;

import org.kamionowski.langid.Model;
import org.kamionowski.langid.ModelReader;

import java.io.IOException;
import java.util.*;

/**
 * User: soldier
 * Date: 23.03.13
 * Time: 14:42
 */
public class CompositeModelReader implements ModelReader {

    private final List<ModelReader> modelReaders;
    private final Map<String, ModelReader> readers;

    public CompositeModelReader(ModelReader... modelReaders) throws IOException {
        this.modelReaders = Arrays.asList(modelReaders);
        this.readers = new HashMap<String, ModelReader>();
        for (ModelReader reader : modelReaders) {
            for (String langId : reader.knownLangIds()) {
                this.readers.put(langId, reader);
            }
        }
    }

    @Override
    public Model read(String langId) throws IOException {
        return findReader(langId).read(langId);
    }

    private ModelReader findReader(String langid) throws IOException {
        if (!readers.containsKey(langid)) {
            throw new IOException("Cannot read model for " + langid);
        }
        return readers.get(langid);
    }

    @Override
    public Set<String> knownLangIds() throws IOException {
        return readers.keySet();
    }

}
