package com.dazong.common.util.io;

import java.io.File;

/**
 * @author Sam
 *
 */
public interface FileVisitor {

    /**
     * 文件遍历
     * @param file
     */
    void visit(File file);

}
