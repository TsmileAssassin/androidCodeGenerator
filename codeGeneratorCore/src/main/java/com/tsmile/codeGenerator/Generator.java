package com.tsmile.codeGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.tsmile.codeGenerator.model.ViewEntity;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 代码生成器<br>
 * 1 使用generateActivity生成对应xml文件的Activity类<br>
 * 2 使用generateFindViewCode生成对应xml文件的findView系列代码<br>
 * @author Tsimle
 * 
 */
public class Generator {

    private Template templateActivity;
    private Template templateFindView;

    private String outputDir;

    public Generator() throws IOException {
        Configuration config = new Configuration();
        config.setClassForTemplateLoading(this.getClass(), "/");
        config.setObjectWrapper(new DefaultObjectWrapper());
        templateActivity = config.getTemplate("activity.ftl");
        templateFindView = config.getTemplate("findViewCode.ftl");
        outputDir = System.getProperty("user.dir") + "//out";
    }

    /**
     * 生成对应actRelateXmlPath的Activity类文件
     * 
     * @param actRelateXmlPath
     * @param className
     * @param packageName
     */
    public void generateActivity(String actRelateXmlPath, String className, String packageName) throws Exception {
        Map<String, Object> root = new HashMap<String, Object>();
        File outDir = getOutputDir();
        File genJAVAFile = getJavaFile(outDir, className);

        try {
            ViewEntity viewEntity = ViewXMLParser.parse(actRelateXmlPath);
            viewEntity.setClassName(className);
            viewEntity.setPackageName(packageName);
            viewEntity.setResourceName(actRelateXmlPath);
            root.put("viewEntity", viewEntity);

            Writer writer = new FileWriter(genJAVAFile);
            try {
                templateActivity.process(root, writer);
                writer.flush();
                System.out.println("success " + genJAVAFile.getCanonicalPath());
            } finally {
                writer.close();
            }
        } catch (Exception ex) {
            System.err.println("for template: " + root);
            System.err.println("Error in generating (" + genJAVAFile.getAbsolutePath() + ")");
            throw ex;
        }
    }
    
    public void generateFindViewCode(String relateXmlPath, String outputFileName) throws Exception {
        Map<String, Object> root = new HashMap<String, Object>();
        File outDir = getOutputDir();
        File genFile = new File(outDir, outputFileName);
        
        try {
            ViewEntity viewEntity = ViewXMLParser.parse(relateXmlPath);
            viewEntity.setResourceName(relateXmlPath);
            root.put("viewEntity", viewEntity);

            Writer writer = new FileWriter(genFile);
            try {
                templateFindView.process(root, writer);
                writer.flush();
                System.out.println("success " + genFile.getCanonicalPath());
            } finally {
                writer.close();
            }
        } catch (Exception ex) {
            System.err.println("for template: " + root);
            System.err.println("Error in generating (" + genFile.getAbsolutePath() + ")");
            throw ex;
        }
    }

    private File getJavaFile(File outDirFile, String javaClassName) {
        File file = new File(outDirFile, javaClassName + ".java");
        return file;
    }

    private File getOutputDir() throws IOException {
        File file = new File(outputDir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
}
