package com.bookroles.controller.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.lang.annotation.*;

/**
 * @Author: dlus91
 * @Date: 2023/10/7 13:44
 */
@SupportedValidationTarget({ValidationTarget.ANNOTATED_ELEMENT})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(VoJudgeSQLInjectValid.List.class)
@Documented
@Constraint(validatedBy = {VoJudgeSQLInjectValidator.class})
public @interface VoJudgeSQLInjectValid {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        VoJudgeSQLInjectValid[] value();
    }

}
