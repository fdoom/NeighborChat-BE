package kit.hackathon.nearbysns.global.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NonExpiredArticleIdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NonExpiredArticleId {

    String message() default "이미 존재하는 이메일입니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
