package com.hpw.utils.validatorgroup;

/**
 * 表示非优先检查组 <br>
 * 比如 createTime 等可能需要用户在最后时刻设置，不会在 dao 入口就检查 <br>
 * 使用方式: <br>
 * <pre>
 *      {@code
 *       @NotNull(message="id 不能为空", groups={NotFirstCheck.class})
 *       private Integer id;
 *      }
 *  </pre>
 * <br>
 * <strong>注意，默认是加入 {@link javax.validation.groups.Default} 组的，如果需要，要在 groups 字段中显式添加该 class </strong>
 */
public interface InsertCheckGroup {
}
