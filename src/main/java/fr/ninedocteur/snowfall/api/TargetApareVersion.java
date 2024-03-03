package fr.ninedocteur.snowfall.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TargetApareVersion {
    String version();
}
