package com.dazong.common.util.io;

import java.io.IOException;
import java.io.InputStream;

public class NullInputStream extends InputStream {

    @Override
    public int read() throws IOException {
        return -1;
    }

}
