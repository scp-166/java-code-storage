package com.hpw.service;

/**
 * mailTemplate配置外部数据源
 */
public interface MailTemplateConfigReader {
    /**
     * 从外部数据源读取 mailTemplate 配置信息
     */
    public abstract void parse();
}
