package com.dazong.cc.common.util.io;

import java.io.IOException;
import java.io.InputStream;

public class NullInputStream extends InputStream {

    public int read() throws IOException {
        return -1;
    }

}
