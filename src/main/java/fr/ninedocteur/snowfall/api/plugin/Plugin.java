package fr.ninedocteur.snowfall.api.plugin;

import be.ninedocteur.apare.api.mod.ApareMod;
import be.ninedocteur.apare.api.mod.ModSide;
import fr.ninedocteur.snowfall.Snowfall;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Plugin {
    Class<? extends SnowfallPlugin> value();
}
