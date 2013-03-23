package org.kamionowski.langid;

import java.io.IOException;
import java.util.Set;

/**
 * User: soldier
 * Date: 22.03.13
 * Time: 23:35
 */
public interface ModelReader {

    Model read(String langId) throws IOException;

    Set<String> knownLangIds() throws IOException;
}
