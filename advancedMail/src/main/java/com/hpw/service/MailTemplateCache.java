package com.hpw.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MailTemplateCache {

    private static Set<Integer> languageTypeSet = new HashSet<>();

    private static Set<Integer> contentTypeSet = new HashSet<>();

    private static Map<Integer, String> systemNameMap = new HashMap<>();

    public static void init(MailSystemNameConfigReader nameConfigReader, MailTemplateConfigReader templateConfigReader) {
        nameConfigReader.parse();
        templateConfigReader.parse();
    }

    public static void addSystemName(Integer languageType, String systemName) {
        systemNameMap.put(languageType, systemName);
    }

    public static Map<Integer, String> getSystemNameMap() {
        return systemNameMap;
    }

    public static String getSystemName(Integer languageType) {
        return systemNameMap.get(languageType);
    }

    public static Set<Integer> getLanguageTypeSet() {
        return languageTypeSet;
    }

    public static void addLanguageType(Integer languageType) {
        languageTypeSet.add(languageType);
    }

    public static Set<Integer> getContentTypeSet() {
        return contentTypeSet;
    }

    public static void addContentType(Integer contentType) {
        contentTypeSet.add(contentType);
    }
}
