package io.github.nickacpt.iicptinwgcwypnttass.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.bukkit.Material;

@Retention(RetentionPolicy.RUNTIME)
public @interface Item {
    Material value() default Material.AIR;
    int amount() default 1;
}
