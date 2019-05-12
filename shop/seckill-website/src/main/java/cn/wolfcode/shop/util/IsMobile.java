package cn.wolfcode.shop.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {MobileConstraintValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(IsMobile.List.class)
public @interface IsMobile {

    String message() default "手机格式不正确";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@code @NotEmpty} constraints on the same element.
     *
     * @see javax.validation.constraints.NotEmpty
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        IsMobile[] value();
    }
}
