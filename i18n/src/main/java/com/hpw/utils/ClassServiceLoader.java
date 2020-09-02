package com.hpw.utils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClassServiceLoader {

    private URLClassLoader classLoader;
    private String classpath;

    private static final class Holder {
        private static final ClassServiceLoader instance = new ClassServiceLoader();
    }

    private ClassServiceLoader() {
        buildClassPath();
    }

    public static ClassServiceLoader getInstance() {
        return Holder.instance;
    }

    public static String getAppPath() {
        ClassServiceLoader loader = getInstance();
        return loader.classLoader.getResource("").getPath();
    }

    private void buildClassPath() {
        this.classLoader = (URLClassLoader) this.getClass().getClassLoader();
        this.classpath = null;
        StringBuilder sb = new StringBuilder();
        for (URL url : this.classLoader.getURLs()) {
            String p = url.getFile();
            sb.append(p).append(File.pathSeparator);
        }
        this.classpath = sb.toString();
		// LogManager.getLogger().debug("脚本加载需要类路径：" + this.classpath);
    }

    public static List<String> getClassInPackage(String pkgName, String paths) {
        List<File> files = new ArrayList<File>();
        String[] pathes = paths.split(File.pathSeparator);
        for (String path : pathes) {
            files.add(new File(path));
        }

        List<String> ret = new ArrayList<String>();
        // com.hpw.xml.mailtemplate -> com/hpw/xml/mailtemplate/
        String rPath = pkgName.replace('.', '/') + "/";
        try {
            for (File classPath : files) {
                if (!classPath.exists()) continue;
                if (classPath.isDirectory()) {
                    File dir = new File(classPath, rPath);
                    if (!dir.exists())
                        continue;
                    for (File file : dir.listFiles()) {
                        if (file.isFile()) {
                            String clsName = file.getName();
                            clsName = pkgName + "." + clsName.substring(0, clsName.length() - 6);
                            ret.add(clsName);
                        }
                    }
                } else {
                    FileInputStream fis = new FileInputStream(classPath);
                    JarInputStream jis = new JarInputStream(fis, false);
                    JarEntry e = null;
                    while ((e = jis.getNextJarEntry()) != null) {
                        String eName = e.getName();
                        if (eName.startsWith(rPath) && !eName.endsWith("/")) {
                            ret.add(eName.replace('/', '.').substring(0, eName.length() - 6));
                        }
                        jis.closeEntry();
                    }
                    jis.close();
                }
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return ret;
    }

    public static List<File> getClassPath(String paths) {
        List<File> ret = new ArrayList<File>();
        String[] pathes = paths.split(File.pathSeparator);
        for (String path : pathes) {
            ret.add(new File(path));
        }
        return ret;
    }

    public List<String> getClassInPackageDefault(String pkgName) {
        return getClassInPackage(pkgName, this.classpath);
    }
}
