package fr.ninedocteur.snowfall.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TargetSnowfallVersion {
    String snowfallRequiredVersion();
    String yourContentVersion();
    boolean supportHigher() default true;
    boolean supportLower() default false;
}
