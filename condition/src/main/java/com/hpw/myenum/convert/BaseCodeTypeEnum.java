package com.hpw.myenum.convert;

/**
 * 表示用于 db 和 javaEnum 进行互转
 * 要求实现类枚举值为 {@link Integer}
 */
public interface BaseCodeTypeEnum {
    public abstract Integer getCode();
}
