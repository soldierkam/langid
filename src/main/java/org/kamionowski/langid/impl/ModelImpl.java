package org.kamionowski.langid.impl;

import org.kamionowski.langid.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * User: soldier
 * Date: 22.03.13
 * Time: 23:37
 */
class ModelImpl implements Model {

    private final Map<String, Integer> d3grams = new HashMap<String, Integer>();
    private final int sum;

    public ModelImpl(InputStream dataStream) throws IOException {
        if (dataStream == null) {
            throw new NullPointerException("Data stream cannot be null");
        }
        BufferedReader br = null;
        try {
            int agg = 0;
            br = new BufferedReader(new InputStreamReader(dataStream));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                Integer c = Integer.parseInt(parts[0]);
                String d3gram = parts[1];
                d3grams.put(d3gram, c);
                agg += c;
            }
            this.sum = agg;
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Override
    public int count() {
        return sum;
    }

    @Override
    public int freq(String trigram) {
        if (!d3grams.containsKey(trigram)) {
            return 0;
        }
        return d3grams.get(trigram);
    }
}
