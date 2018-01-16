package com.dazong.common.util.io;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.PlatformException;
import com.dazong.common.util.Assert;
import com.dazong.common.util.CharsetUtil;
import com.dazong.common.util.StringUtil;
import com.dazong.common.util.reflect.ClassWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件工具类
 *
 * @author
 * @date 2018/01/12
 */
public class FileUtil {

    private FileUtil() {

    }

    public static boolean isInJar(File file) {
        return isInJar(file.getAbsolutePath());
    }

    public static boolean isInJar(String filePath) {
        return filePath.contains(".jar!");
    }

    /**
     * 读取 UTF-8 文件全部内容
     *
     * @param path 文件路径
     * @return 文件内容
     */
    public static String read(String path) {
        File f = FileUtil.findFile(path);
        if (null == f) {
            throw new PlatformException(CommonStatus.FAIL, "Can not find file '%s'", path);
        }
        return read(f);
    }

    /**
     * 读取 UTF-8 文件全部内容
     *
     * @param f 文件
     * @return 文件内容
     */
    public static String read(File f) {
        return StreamUtil.readAll(StreamUtil.fileInr(f));
    }

    /**
     * 读取文件全部字节，并关闭文件
     *
     * @param path 文件路径
     * @return 文件的字节内容
     */
    public static byte[] readBytes(String path) {
        File f = FileUtil.findFile(path);
        if (null == f) {
            throw new PlatformException(CommonStatus.FAIL, "Can not find file '%s'", path);
        }
        return readBytes(f);
    }

    /**
     * 读取文件全部字节，并关闭文件
     *
     * @param f 文件
     * @return 文件的字节内容
     */
    public static byte[] readBytes(File f) {
        return StreamUtil.readBytesAndClose(StreamUtil.buff(StreamUtil.fileIn(f)));
    }

    /**
     * 将内容写到一个文件内，内容对象可以是：
     * <ul>
     * <li>InputStream - 按二进制方式写入
     * <li>byte[] - 按二进制方式写入
     * <li>Reader - 按 UTF-8 方式写入
     * <li>其他对象被 toString() 后按照 UTF-8 方式写入
     * </ul>
     *
     * @param path 文件路径，如果不存在，则创建
     * @param obj  内容对象
     */
    public static void write(String path, Object obj) {
        if (null == path || null == obj) {
            return;
        }
        try {
            write(FileUtil.createFileIfNoExists(path), obj);
        } catch (IOException e) {
            throw new PlatformException(e, CommonStatus.FAIL, "write");
        }
    }

    /**
     * 将内容写到一个文件内，内容对象可以是：
     * <p>
     * <ul>
     * <li>InputStream - 按二进制方式写入
     * <li>byte[] - 按二进制方式写入
     * <li>Reader - 按 UTF-8 方式写入
     * <li>其他对象被 toString() 后按照 UTF-8 方式写入
     * </ul>
     *
     * @param f   文件
     * @param obj 内容
     */
    public static void write(File f, Object obj) {
        if (null == f || null == obj) {
            return;
        }
        if (f.isDirectory()) {
            throw new PlatformException(CommonStatus.FAIL, "Directory '%s' can not be write as File 1", f);
        }

        try {
            // 保证文件存在
            if (!f.exists()) {
                FileUtil.createNewFile(f);
            }
            // 输入流
            if (obj instanceof InputStream) {
                StreamUtil.writeAndClose(StreamUtil.fileOut(f), (InputStream) obj);
            }
            // 字节数组
            else if (obj instanceof byte[]) {
                StreamUtil.writeAndClose(StreamUtil.fileOut(f), (byte[]) obj);
            }
            // 文本输入流
            else if (obj instanceof Reader) {
                StreamUtil.writeAndClose(StreamUtil.fileOutw(f), (Reader) obj);
            }
            // 其他对象
            else {
                StreamUtil.writeAndClose(StreamUtil.fileOutw(f), obj.toString());
            }
        } catch (IOException e) {
            throw new PlatformException(e, CommonStatus.FAIL, "Directory '%s' can not be write as File", f);
        }
    }

    /**
     * 将内容写到一个文件末尾
     * <ul>
     * <li>InputStream - 按二进制方式写入
     * <li>byte[] - 按二进制方式写入
     * <li>Reader - 按 UTF-8 方式写入
     * <li>其他对象被 toString() 后按照 UTF-8 方式写入
     * </ul>
     *
     * @param f   文件
     * @param obj 内容
     */
    public static void appendWrite(File f, Object obj) {
        if (null == f || null == obj) {
            return;
        }
        if (f.isDirectory()) {
            throw new PlatformException(CommonStatus.FAIL, "Directory '%s' can not be write as File", f);
        }

        try {
            // 保证文件存在
            if (!f.exists()) {
                FileUtil.createNewFile(f);
            }
            // 输入流
            if (obj instanceof InputStream) {
                // TODO
                throw new PlatformException(CommonStatus.FAIL);
            }
            // 字节数组
            else if (obj instanceof byte[]) {
                // TODO
                throw new PlatformException(CommonStatus.FAIL);
            }
            // 文本输入流
            else if (obj instanceof Reader) {
                // TODO
                throw new PlatformException(CommonStatus.FAIL);
            }
            // 其他对象
            else {
                StreamUtil.appendWriteAndClose(f, obj.toString());
            }
        } catch (IOException e) {
            throw new PlatformException(e, CommonStatus.FAIL, "将内容写到一个文件末尾");
        }
    }

    /**
     * 将文件后缀改名，从而生成一个新的文件对象。但是并不在磁盘上创建它
     *
     * @param f      文件
     * @param suffix 新后缀， 比如 ".gif" 或者 ".jpg"
     * @return 新文件对象
     */
    public static File renameSuffix(File f, String suffix) {
        if (null == f || null == suffix || suffix.length() == 0) {
            return f;
        }
        return new File(renameSuffix(f.getAbsolutePath(), suffix));
    }

    /**
     * 将文件路径后缀改名，从而生成一个新的文件路径。
     *
     * @param path   文件路径
     * @param suffix 新后缀， 比如 ".gif" 或者 ".jpg"
     * @return 新文件后缀
     */
    public static String renameSuffix(String path, String suffix) {
        int pos = path.length();
        for (--pos; pos > 0; pos--) {
            if (path.charAt(pos) == '.') {
                break;
            }
            if (path.charAt(pos) == '/' || path.charAt(pos) == '\\') {
                pos = -1;
                break;
            }
        }
        if (0 >= pos) {
            return path + suffix;
        }
        return path.substring(0, pos) + suffix;
    }

    /**
     * 获取文件主名。 即去掉后缀的名称
     *
     * @param path 文件路径
     * @return 文件主名
     */
    public static String getMajorName(String path) {
        int len = path.length();
        int l = 0;
        int r = len;
        for (int i = r - 1; i > 0; i--) {
            if (r == len && path.charAt(i) == '.') {
                    r = i;
            }
            if (path.charAt(i) == '/' || path.charAt(i) == '\\') {
                l = i + 1;
                break;
            }
        }
        return path.substring(l, r);
    }

    /**
     * 获取文件主名。 即去掉后缀的名称
     *
     * @param f 文件
     * @return 文件主名
     */
    public static String getMajorName(File f) {
        return getMajorName(f.getAbsolutePath());
    }

    /**
     * 获取文件后缀名，不包括 '.'，如 'abc.gif','，则返回 'gif'
     *
     * @param f 文件
     * @return 文件后缀名
     */
    public static String getSuffixName(File f) {
        if (null == f) {
            return null;
        }
        return getSuffixName(f.getAbsolutePath());
    }

    /**
     * 获取文件后缀名，不包括 '.'，如 'abc.gif','，则返回 'gif'
     *
     * @param path 文件路径
     * @return 文件后缀名
     */
    public static String getSuffixName(String path) {
        if (null == path) {
            return null;
        }
        int pos = path.lastIndexOf('.');
        if (-1 == pos) {
            return "";
        }
        return path.substring(pos + 1);
    }

    /**
     * 根据正则式，从压缩文件中获取文件
     *
     * @param zip   压缩文件
     * @param regex 正则式，用来匹配文件名
     * @return 数组
     */
    public static ZipEntry[] findEntryInZip(ZipFile zip, String regex) {
        List<ZipEntry> list = new LinkedList<ZipEntry>();
        Enumeration<? extends ZipEntry> en = zip.entries();
        while (en.hasMoreElements()) {
            ZipEntry ze = en.nextElement();
            if (null == regex || ze.getName().matches(regex)) {
                list.add(ze);
            }
        }
        return list.toArray(new ZipEntry[list.size()]);
    }

    /**
     * 试图生成一个文件对象，如果文件不存在则创建它。 如果给出的 PATH 是相对路径 则会在 CLASSPATH
     * 中寻找，如果未找到，则会在用户主目录中创建这个文件
     *
     * @param path 文件路径，可以以 ~ 开头，也可以是 CLASSPATH 下面的路径
     * @return 文件对象
     * @throws IOException 创建失败
     */
    public static File createFileIfNoExists(String path) throws IOException {
        String thePath = DiskUtil.absolute(path);
        if (null == thePath) {
            thePath = DiskUtil.normalize(path);
        }
        File f = new File(thePath);
        if (!f.exists()) {
            FileUtil.createNewFile(f);
        }
        if (!f.isFile()) {
            throw new PlatformException(CommonStatus.FAIL, "'%s' should be a file!", path);
        }
        return f;
    }

    /**
     * 如果文件对象不存在，则创建它
     *
     * @param f 文件对象
     * @return 传入的文件对象，以便为调用者省略一行代码
     */
    public static File createFileIfNoExists(File f) {
        if (null == f) {
            return f;
        }
        if (!f.exists()) {
            try {
                FileUtil.createNewFile(f);
            } catch (IOException e) {
                throw new PlatformException(e, CommonStatus.FAIL, "如果文件对象不存在，则创建它");
            }
        }
        if (!f.isFile()) {
            throw new PlatformException(CommonStatus.FAIL, "'%s' should be a file!", f);
        }
        return f;
    }

    /**
     * 试图生成一个目录对象，如果文件不存在则创建它。 如果给出的 PATH 是相对路径 则会在 CLASSPATH
     * 中寻找，如果未找到，则会在用户主目录中创建这个目录
     *
     * @param path 文件路径，可以以 ~ 开头，也可以是 CLASSPATH 下面的路径
     * @return 文件对象
     */
    public static File createDirIfNoExists(String path) {
        String thePath = DiskUtil.absolute(path);
        if (null == thePath) {
            thePath = DiskUtil.normalize(path);
        }
        File f = new File(thePath);
        if (!f.exists()) {
            FileUtil.makeDir(f);
        }
        if (!f.isDirectory()) {
            throw new PlatformException(CommonStatus.FAIL, "'%s' should be a directory!", path);
        }
        return f;
    }

    /**
     * 传入一个目录对象，如果目录不存在，则创建目录
     *
     * @param d 文件目录对象
     * @return 文件目录对象，以便调用者省略一行代码
     */
    public static File createDirIfNoExists(File d) {
        if (null == d) {
            return d;
        }
        if (!d.exists()) {
            FileUtil.makeDir(d);
        }
        if (!d.isDirectory()) {
            throw new PlatformException(CommonStatus.FAIL, "'%s' should be a directory!", d);
        }
        return d;
    }

    /**
     * 从 CLASSPATH 下或从指定的本机器路径下寻找一个文件
     *
     * @param path        文件路径
     * @param klassLoader 参考 ClassLoader
     * @param enc         文件路径编码
     * @return 文件对象，如果不存在，则为 null
     */
    public static File findFile(String path, ClassLoader klassLoader, String enc) {
        path = DiskUtil.absolute(path, klassLoader, enc);
        if (null == path) {
            return null;
        }
        return new File(path);
    }

    /**
     * 从 CLASSPATH 下或从指定的本机器路径下寻找一个文件
     *
     * @param path 文件路径
     * @param enc  文件路径编码
     * @return 文件对象，如果不存在，则为 null
     */
    public static File findFile(String path, String enc) {
        return findFile(path, ClassWrapper.getClassLoader(), enc);
    }

    /**
     * 从 CLASSPATH 下或从指定的本机器路径下寻找一个文件
     *
     * @param path        文件路径
     * @param klassLoader 使用该 ClassLoader进行查找
     * @return 文件对象，如果不存在，则为 null
     */
    public static File findFile(String path, ClassLoader klassLoader) {
        return findFile(path, klassLoader, CharsetUtil.defaultEncoding());
    }

    /**
     * 从 CLASSPATH 下或从指定的本机器路径下寻找一个文件
     *
     * @param path 文件路径
     * @return 文件对象，如果不存在，则为 null
     */
    public static File findFile(String path) {
        return findFile(path, ClassWrapper.getClassLoader(), CharsetUtil.defaultEncoding());
    }

    /**
     * 获取输出流
     *
     * @param path 文件路径
     * @return 输出流
     */
    public static InputStream findFileAsStream(String path) {
        return findFileAsStream(path, FileUtil.class);
    }

    /**
     * 获取输出流
     *
     * @param path  文件路径
     * @param klass 参考的类， -- 会用这个类的 ClassLoader
     * @return 输出流
     */
    public static InputStream findFileAsStream(String path, Class<?> klass) {
        File f = new File(path);
        if (f.exists()) {
            try {
                return new FileInputStream(f);
            } catch (FileNotFoundException e1) {
                return null;
            }
        }
        if (null != klass) {
            InputStream ins = klass.getClassLoader().getResourceAsStream(path);
            if (null == ins) {
                ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            }
            if (null != ins) {
                return ins;
            }
        }
        return ClassLoader.getSystemResourceAsStream(path);
    }

    /**
     * 文件对象是否是目录，可接受 null
     */
    public static boolean isDirectory(File f) {
        if (null == f) {
            return false;
        }
        if (!f.exists()) {
            return false;
        }
        return f.isDirectory();
    }

    /**
     * 文件对象是否是文件，可接受 null
     */
    public static boolean isFile(File f) {
        return null != f && f.exists() && f.isFile();
    }

    /**
     * 创建新文件，如果父目录不存在，也一并创建。可接受 null 参数
     *
     * @param f 文件对象
     * @return false，如果文件已存在。 true 创建成功
     * @throws IOException
     */
    public static boolean createNewFile(File f) throws IOException {
        if (null == f || f.exists()) {
            return false;
        }
        makeDir(f.getParentFile());
        return f.createNewFile();
    }

    /**
     * 创建新目录，如果父目录不存在，也一并创建。可接受 null 参数
     *
     * @param dir 目录对象
     * @return false，如果目录已存在。 true 创建成功
     * @throws IOException
     */
    public static boolean makeDir(File dir) {
        return !(null == dir || dir.exists()) && dir.mkdirs();
    }

    /**
     * 强行删除一个目录，包括这个目录下所有的子目录和文件
     *
     * @param dir 目录
     * @return 是否删除成功
     */
    public static boolean deleteDir(File dir) {
        if (null == dir || !dir.exists()) {
            return false;
        }
        if (!dir.isDirectory()) {
            throw new PlatformException(CommonStatus.FAIL,"\"" + dir.getAbsolutePath() + "\" should be a directory!");
        }
        File[] files = dir.listFiles();
        boolean re = false;
        if (null != files) {
            if (files.length == 0) {
                return dir.delete();
            }
            for (File f : files) {
                if (f.isDirectory()) {
                    re |= deleteDir(f);
                } else {
                    re |= deleteFile(f);
                }
            }
            re |= dir.delete();
        }
        return re;
    }

    /**
     * 删除一个文件
     *
     * @param f 文件
     * @return 是否删除成功
     * @throws IOException
     */
    public static boolean deleteFile(File f) {
        return null != f && f.delete();
    }

    /**
     * 清除一个目录里所有的内容
     *
     * @param dir 目录
     * @return 是否清除成功
     */
    public static boolean clearDir(File dir) {
        if (null == dir) {
            return false;
        }
        if (!dir.exists()) {
            return false;
        }
        File[] fs = dir.listFiles();
        for (File f : fs) {
            if (f.isFile()) {
                FileUtil.deleteFile(f);
            } else if (f.isDirectory()) {
                FileUtil.deleteDir(f);
            }
        }
        return false;
    }

    /**
     * 拷贝一个文件
     *
     * @param src    原始文件
     * @param target 新文件
     * @return 是否拷贝成功
     * @throws IOException
     */
    public static boolean copyFile(File src, File target) throws IOException {
        if (src == null || target == null || !src.exists()) {
            return false;
        }
        if (!target.exists() && !createNewFile(target)) {
            return false;
        }
        InputStream ins = new BufferedInputStream(new FileInputStream(src));
        OutputStream ops = new BufferedOutputStream(new FileOutputStream(target));

        StreamUtil.write(ops, ins);

        StreamUtil.safeClose(ins);
        StreamUtil.safeFlush(ops);
        StreamUtil.safeClose(ops);
        return target.setLastModified(src.lastModified());
    }

    /**
     * 自动决定是 copy 文件还是目录
     *
     * @param src    源
     * @param target 目标
     * @return 是否 copy 成功
     */
    public static boolean copy(File src, File target) {
        try {
            if (src.isDirectory()) {
                return copyDir(src, target);
            }
            return copyFile(src, target);
        } catch (IOException e) {
            throw new PlatformException(e, CommonStatus.FAIL, "自动决定是 copy 文件还是目录");
        }
    }

    /**
     * 拷贝一个目录
     *
     * @param src    原始目录
     * @param target 新目录
     * @return 是否拷贝成功
     * @throws IOException
     */
    public static boolean copyDir(File src, File target) throws IOException {
        if (src == null || target == null || !src.exists()) {
            return false;
        }
        if (!src.isDirectory()) {
            throw new IOException(src.getAbsolutePath() + " should be a directory!");
        }
        if (!target.exists()) {
            if (!makeDir(target)) {
                return false;
            }
        }
        boolean re = true;
        File[] files = src.listFiles();
        if (null != files) {
            for (File f : files) {
                if (f.isFile()) {
                    re &= copyFile(f, new File(target.getAbsolutePath() + "/" + f.getName()));
                } else {
                    re &= copyDir(f, new File(target.getAbsolutePath() + "/" + f.getName()));
                }
            }
        }
        return re;
    }

    /**
     * 将文件移动到新的位置
     *
     * @param src    原始文件
     * @param target 新文件
     * @return 移动是否成功
     * @throws IOException
     */
    public static boolean move(File src, File target) throws IOException {
        if (src == null || target == null) {
            return false;
        }
        makeDir(target.getParentFile());
        if (src.isDirectory()) {
            src = new File(src.getCanonicalPath() + File.separator);
            target = new File(target.getCanonicalPath() + File.separator);
        }
        return src.renameTo(target);
    }

    /**
     * 将文件改名
     *
     * @param src     文件
     * @param newName 新名称
     * @return 改名是否成功
     */
    public static boolean rename(File src, String newName) {
        if (src == null || newName == null) {
            return false;
        }
        if (src.exists()) {
            File newFile = new File(src.getParent() + "/" + newName);
            if (newFile.exists()) {
                return false;
            }
            FileUtil.makeDir(newFile.getParentFile());
            return src.renameTo(newFile);
        }
        return false;
    }

    /**
     * 修改路径
     *
     * @param path    路径
     * @param newName 新名称
     * @return 新路径
     */
    public static String renamePath(String path, String newName) {
        if (StringUtil.isNotBlank(path)) {
            int pos = path.replace('\\', '/').lastIndexOf('/');
            if (pos > 0) {
                return path.substring(0, pos) + "/" + newName;
            }
        }
        return newName;
    }

    /**
     * @param path 路径
     * @return 父路径
     */
    public static String getParent(String path) {
        if (Strings.isBlank(path)) {
            return path;
        }
        int pos = path.replace('\\', '/').lastIndexOf('/');
        if (pos > 0) {
            return path.substring(0, pos);
        }
        return "/";
    }

    /**
     * @param path 全路径
     * @return 文件或者目录名
     */
    public static String getName(String path) {
        if (StringUtil.isNotBlank(path)) {
            int pos = path.replace('\\', '/').lastIndexOf('/');
            if (pos != -1) {
                return path.substring(pos + 1);
            }
        }
        return path;
    }

    /**
     * 将一个目录下的特殊名称的目录彻底删除，比如 '.svn' 或者 '.cvs'
     *
     * @param dir  目录
     * @param name 要清除的目录名
     * @throws IOException
     */
    public static void cleanAllFolderInSubFolderes(File dir, String name) throws IOException {
        File[] files = dir.listFiles();
        for (File d : files) {
            if (d.isDirectory()) {
                if (d.getName().equalsIgnoreCase(name)) {
                    deleteDir(d);
                } else {
                    cleanAllFolderInSubFolderes(d, name);
                }
            }
        }
    }

    /**
     * 精确比较两个文件是否相等
     *
     * @param f1 文件1
     * @param f2 文件2
     * @return <ul>
     * <li>true: 两个文件内容完全相等
     * <li>false: 任何一个文件对象为 null，不存在 或内容不相等
     * </ul>
     */
    public static boolean isEquals(File f1, File f2) {
        if (null == f1 || null == f2 || !f1.isFile() || !f2.isFile()) {
            return false;
        }
        InputStream ins1 = null;
        InputStream ins2 = null;
        try {
            ins1 = StreamUtil.fileIn(f1);
            ins2 = StreamUtil.fileIn(f2);
            return StreamUtil.equals(ins1, ins2);
        } catch (IOException e) {
            return false;
        } finally {
            StreamUtil.safeClose(ins1);
            StreamUtil.safeClose(ins2);
        }
    }

    /**
     * 在一个目录下，获取一个文件对象
     *
     * @param dir  目录
     * @param path 文件相对路径
     * @return 文件
     */
    public static File getFile(File dir, String path) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                return new File(dir.getAbsolutePath() + "/" + path);
            }
            return new File(dir.getParent() + "/" + path);
        }
        return new File(path);
    }

    /**
     * 获取一个目录下所有子目录。子目录如果以 '.' 开头，将被忽略
     *
     * @param dir 目录
     * @return 子目录数组
     */
    public static File[] dirs(File dir) {
        return dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.isHidden() && f.isDirectory() && !f.getName().startsWith(".");
            }
        });
    }

    /**
     * 递归查找获取一个目录下所有子目录(及子目录的子目录)。子目录如果以 '.' 开头，将被忽略
     * <p/>
     * <b>包含传入的目录</b>
     *
     * @param dir 目录
     * @return 子目录数组
     */
    public static File[] scanDirs(File dir) {
        ArrayList<File> list = new ArrayList<>();
        list.add(dir);
        scanDirs(dir, list);
        return list.toArray(new File[list.size()]);
    }

    private static void scanDirs(File rootDir, List<File> list) {
        File[] dirs = rootDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.isHidden() && f.isDirectory() && !f.getName().startsWith(".");
            }
        });
        if (dirs != null) {
            for (File dir : dirs) {
                scanDirs(dir, list);
                list.add(dir);
            }
        }
    }

    /**
     * 获取一个目录下所有的文件(不递归，仅仅一层)。隐藏文件会被忽略。
     *
     * @param dir    目录
     * @param suffix 文件后缀名。如果为 null，则获取全部文件
     * @return 文件数组
     */
    public static File[] files(File dir, final String suffix) {
        return dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.isHidden()
                        && f.isFile()
                        && (null == suffix || f.getName().endsWith(suffix));
            }
        });
    }

    /**
     * 判断两个文件内容是否相等
     *
     * @param f1 文件对象
     * @param f2 文件对象
     * @return <ul>
     * <li>true: 两个文件内容完全相等
     * <li>false: 任何一个文件对象为 null，不存在 或内容不相等
     * </ul>
     */
    public static boolean equals(File f1, File f2) {
        return isEquals(f1, f2);
    }

    public static boolean copyOnWrite(File f, Object obj) {
        File tmp = new File(f.getAbsolutePath() + ".new");
        File tmp2 = new File(f.getAbsolutePath() + ".old");
        tmp2.delete();
        try {
            write(tmp, obj);
            boolean flag = false;
            if (f.exists()) {
                flag = f.renameTo(tmp2);
            }
            if (tmp.renameTo(f)) {
                tmp2.delete();
                return true;
            } else if (flag) {
                tmp2.renameTo(f); // 如果这里也失败的话,起码.old还在...
            }
            return false;
        } finally {
            tmp.delete();
        }
    }

    public static boolean copyOnWrite(String path, Object obj) {
        return copyOnWrite(new File(path), obj);
    }

    public static List<String> readLines(File f) {
        List<String> lines = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = StreamUtil.buffr(StreamUtil.fileInr(f));
            while (br.ready()) {
                lines.add(br.readLine());
            }
        } catch (IOException e) {
            throw new PlatformException(e, CommonStatus.FAIL, "readLines");
        } finally {
            StreamUtil.safeClose(br);
        }
        return lines;
    }


    /**
     * 根据指定的路径装载一个Properties文件
     *
     * @param loadPath
     * @return
     */
    public static Properties loadPropertiesFile(String loadPath) {
        Assert.isEmpty(loadPath, "the properites file path can't be null, please check it!");
        Properties prop = new Properties();
        try {
            loadPath = StringUtils.trim(loadPath);
            if (loadPath.indexOf("/") == 0) {
                prop.load(FileUtil.class.getResourceAsStream(loadPath));
            } else {
                prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(loadPath));
            }
        } catch (IOException e) {
            throw new PlatformException(e, CommonStatus.FAIL, "load properties file['%s'] is error,cause is:'%s'", loadPath, e.getMessage());
        }
        return prop;
    }
}
