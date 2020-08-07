package com.hpw.utils;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class ComValidatorUtil {
    private ComValidatorUtil() {
    }

    private static Validator normalValidator;

    /**
     * 设置为 快速失败返回的 validator
     */
    private static Validator failFastValidator;

    private static final String SEPARATOR = ", ";


    static {
        // 非快速失败模式
        normalValidator = Validation.buildDefaultValidatorFactory().getValidator();


        // 快速失败模式
        // https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-fail-fast
        failFastValidator = Validation.byProvider(HibernateValidator.class)
                .configure()
                // .addProperty("hibernate.validator.fail_fast", "true") // 可以用下面行代替
                .failFast(true)
                .buildValidatorFactory()
                .getValidator();
    }

    /**
     * 根据对象的校验字段进行校验，快速失败模式
     * <pre>
     *     {@code
     *     // 假设 obj 是个带检测字段的对象
     *     String errInfo = ComValidatorUtil.checkValidateEntityFailFast(obj);
     *        if(Objects.noNull(errInfo)) {
     *            // 进行记录或者其他操作
     *        }
     *     }
     * </pre>
     *
     * @param object 待校验对象
     * @param groups 代校验组, 如果仅仅校验默认组(即{@link javax.validation.groups.Default}) 则不需要进行传入
     * @return 校验不通过，则返回异常信息，否则返回 {@code null}
     */
    public static String validateEntityFailFast(Object object, Class<?>... groups) {
        if (Objects.isNull(object)) {
            return "validator待检测参数不能为空";
        }
        Set<ConstraintViolation<Object>> constraintViolations = failFastValidator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<Object>> iterator = constraintViolations.iterator();
            // only one
            if (iterator.hasNext()) {
                return iterator.next().getMessage();
            }
        }
        return null;
    }

    /**
     * 根据对象的校验字段进行校验，非快速失败模式
     * <pre>
     *     {@code
     *     // 假设 obj 是个带检测字段的对象
     *     String errInfo = ComValidatorUtil.checkValidateEntity(obj);
     *        if(Objects.noNull(errInfo)) {
     *            // 进行记录或者其他操作
     *        }
     *     }
     * </pre>
     *
     * @param object 待校验对象
     * @param groups 代校验组, 如果仅仅校验默认组(即{@link javax.validation.groups.Default}) 则不需要进行传入
     * @return 校验不通过，则返回异常信息，否则返回 {@code null}
     */
    public static String validateEntity(Object object, Class<?>... groups) {
        if (Objects.isNull(object)) {
            return "validator待检测参数不能为空";
        }
        Set<ConstraintViolation<Object>> constraintViolations = normalValidator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            Iterator<ConstraintViolation<Object>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                builder.append(iterator.next().getMessage());
                if (iterator.hasNext()) {
                    builder.append(SEPARATOR);
                }
            }
            return builder.toString();
        }
        return null;
    }

}