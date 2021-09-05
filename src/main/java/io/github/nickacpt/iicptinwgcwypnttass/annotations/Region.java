package io.github.nickacpt.iicptinwgcwypnttass.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Region {
    int x() default 1;

    int y() default 1;

    int width() default 1;

    int height() default 1;
}
