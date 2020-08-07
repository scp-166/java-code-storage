package com.hpw.dao.enummapping;

import com.hpw.myenum.mappingValue.BaseCodeTypeEnum;
import org.apache.ibatis.type.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 限定 {@link BaseCodeTypeEnum} 的通用枚举处理器
 * @param <E>
 */
public class CodeTypeEnumHandler<E extends Enum<?> & BaseCodeTypeEnum> extends BaseTypeHandler<BaseCodeTypeEnum> {
    private Class<E> enumClass;

    public CodeTypeEnumHandler(Class<E> enumClass) {
        if (Objects.isNull(enumClass)) {
            throw new RuntimeException("Type argument cannot be null");
        }
        this.enumClass = enumClass;
    }

    /**
     * 定义设置参数时，该如何把 java 类型的参数转换为对应的数据库类型
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseCodeTypeEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    /**
     * 定义通过字段名称获取字段数据时，如何把数据库类型转换为对应的java类型
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull()? null: searchCode(enumClass, code);
    }

    /**
     * 定义通过字段索引获取字段数据时，如何把数据库类型转换为对应的java类型
     */
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull()? null: searchCode(enumClass, code);
    }

    /**
     * 定义调用存储过程后，如何把数据库类型转换为对应的java类型
     */
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull()? null: searchCode(enumClass, code);
    }

    /**
     * 查询 code 对应的枚举类型
     * @param code 用于查询具体枚举值的 code
     * @param <E> 实现 BaseCodeEnum 的 枚举
     * @return 如果找到，则返回具体的枚举值，否则返回 {@code null}
     */
     private static <E extends Enum<?> & BaseCodeTypeEnum> E searchCode(Class<E> eClass, int code) {
        E[] enumConstants = eClass.getEnumConstants();
        for (E enumItem : enumConstants) {
            if (enumItem.getCode().equals(code)) {
                return enumItem;
            }
        }
        return null;
    }


}
