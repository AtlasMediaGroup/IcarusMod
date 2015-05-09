package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank.Rank;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandParameters
{
    String name();

    String description();

    String usage();

    String aliases() default "";

    Rank rank() default Rank.OP;
}
