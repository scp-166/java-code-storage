package com.hpw.service;


import java.util.*;

public class MailTemplateCache {
    /**
     * {languageType:{contentType: Mail}}
     */
    private static Map<Integer, Map<Integer, TemplateFormatContent>> LANGUAGE_2_TYPE_TEMPLATE_MAP = new HashMap<>();

    /**
     * {languageType:{contentType: Mail}}
     */
    private static Map<Integer, Map<Integer, TemplateFormatContent>> GLOBAL_LANGUAGE_2_TYPE_TEMPLATE_MAP = new HashMap<>();

    /**
     * languageType: system_name
     */
    private static Map<Integer, String> SYSTEM_NAME_MAP = new HashMap<>();

    /**
     * contentType: desc
     */
    private static Map<Integer, String> CONTENT_TYPE_MAP = new HashMap<>();

    /**
     * 默认的模板
     */
    private static TemplateFormatContent DEFAULT_TEMPLATE;

    public static void init(MailSystemNameConfigReader nameConfigReader,  MailTemplateConfigReader templateConfigReader) {
        loadSystemName(nameConfigReader);
        loadTemplate(templateConfigReader);
    }

    /**
     * 加载 mailTemplate 数据源
     *
     * @param templateConfigReader mailTemplate 数据源
     */
    private static void loadTemplate(MailTemplateConfigReader templateConfigReader) {
        templateConfigReader.parse();
    }

    /**
     * 加载 systemName 数据源
     * @param nameConfigReader systemName 数据源
     */
    private static void loadSystemName(MailSystemNameConfigReader nameConfigReader) {
        nameConfigReader.parse();
    }

    public static void addLanguage2TypeTemplate(Integer languageType, Integer contentType, TemplateFormatContent templateFormatContent) {
        LANGUAGE_2_TYPE_TEMPLATE_MAP.putIfAbsent(languageType, new HashMap<>());
        LANGUAGE_2_TYPE_TEMPLATE_MAP.get(languageType).put(contentType, templateFormatContent);
    }

    public static void addGlobalLanguage2TypeTemplate(Integer languageType, Integer contentType, TemplateFormatContent templateFormatContent) {
        GLOBAL_LANGUAGE_2_TYPE_TEMPLATE_MAP.putIfAbsent(languageType, new HashMap<>());
        GLOBAL_LANGUAGE_2_TYPE_TEMPLATE_MAP.get(languageType).put(contentType, templateFormatContent);
    }

    public static void addSystemName(Integer languageId, String systemName) {
        SYSTEM_NAME_MAP.put(languageId, systemName);
    }

    public static void addContentType(Integer typeId, String desc) {
        CONTENT_TYPE_MAP.put(typeId, desc);
    }

    public static void setDefaultTemplate(TemplateFormatContent defaultTemplate) {
        DEFAULT_TEMPLATE = defaultTemplate;
    }

    public static Map<Integer, Map<Integer, TemplateFormatContent>> getLanguage2TypeTemplateMap() {
        return LANGUAGE_2_TYPE_TEMPLATE_MAP;
    }

    public static Map<Integer, String> getSystemNameMap() {
        return SYSTEM_NAME_MAP;
    }

    public static Map<Integer, String> getContentTypeMap() {
        return CONTENT_TYPE_MAP;
    }

    public static TemplateFormatContent getDefaultTemplate() {
        return DEFAULT_TEMPLATE;
    }

    public static Map<Integer, Map<Integer, TemplateFormatContent>> getGlobalLanguage2TypeTemplateMap() {
        return GLOBAL_LANGUAGE_2_TYPE_TEMPLATE_MAP;
    }

    /**
     * 配置文件中存储的简单内容
     */
    public static class TemplateFormatContent {
        private String subject;
        private String content;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Mail{" +
                    "subject='" + subject + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
