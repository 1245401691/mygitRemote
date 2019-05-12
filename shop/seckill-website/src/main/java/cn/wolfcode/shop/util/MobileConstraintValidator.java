package cn.wolfcode.shop.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileConstraintValidator implements ConstraintValidator<IsMobile,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ValidatorUtil.isMobile(value);
    }
}
