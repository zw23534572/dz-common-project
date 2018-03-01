package com.dazong.persistence.generatecode;


import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Output {
    /**
     * 模板位置
     */
    public static final String TEMPLATE_FILE_PATH = "/template/";

    public static GroupTemplate gt;

    private static GroupTemplate getBeetlConfiguration() {
        try {
            System.out.println("Output getBeetlConfiguration started");
            ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
            Configuration cfg = Configuration.defaultConfiguration();
            return new GroupTemplate(resourceLoader, cfg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param templatePath 模板路径
     * @param outputPath   输出路径
     * @param data         数据集
     */
    public static void createFile(String templatePath, String outputPath, Map<String, Object> data) {
        if (gt == null) {
            gt = getBeetlConfiguration();
        }
        File file = new File(outputPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            Template template = gt.getTemplate(TEMPLATE_FILE_PATH + templatePath);
            template.binding(data);
            template.renderTo(new FileOutputStream(file));
            System.out.println(outputPath + "生成成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}



