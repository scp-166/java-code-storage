package com.hpw;

public interface AbstractMapperFactory {
    void init(String configDir) throws Throwable;
    <T> T getMapper(Class<T> clz);
}
