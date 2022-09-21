package com.weiho.scaffold.common.util.validation;

import cn.hutool.core.util.ObjectUtil;
import com.weiho.scaffold.common.exception.BadRequestException;
import lombok.experimental.UtilityClass;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

/**
 * @author Weiho
 * @since 2022/8/30
 */
@UtilityClass
public class ValidationUtils {
    /**
     * 验证空
     */
    public void isNull(Object obj, String entity, String parameter, Object value) {
        if (ObjectUtil.isNull(obj)) {
            String msg = entity + " 不存在: " + parameter + " is " + value;
            throw new BadRequestException(msg);
        }
    }

    /**
     * 验证是否为邮箱
     */
    public boolean isEmail(String email) {
        return new EmailValidator().isValid(email, null);
    }
}
