package com.redmagic.undefinedapi.extension

import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.util.ConfigurationBuilder
import java.lang.reflect.Method

fun JavaPlugin.findClasses(annotation: Class<out Annotation>): Set<Class<*>> = Reflections(
    ConfigurationBuilder()
        .forPackages(this::class.java.packageName)
        .setScanners(SubTypesScanner(false), ResourcesScanner())
).getTypesAnnotatedWith(annotation)

fun JavaPlugin.findMethods(annotation: Class<out Annotation>): MutableSet<Method> = Reflections(
    ConfigurationBuilder()
        .forPackages(this::class.java.packageName)
        .setScanners(SubTypesScanner(false), ResourcesScanner())
).getMethodsAnnotatedWith(annotation)