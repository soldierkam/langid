package org.kamionowski.langid;

import java.io.IOException;

/**
 * User: soldier
 * Date: 22.03.13
 * Time: 23:35
 */
public interface ModelReader {
    Model read(String country) throws IOException;
}
