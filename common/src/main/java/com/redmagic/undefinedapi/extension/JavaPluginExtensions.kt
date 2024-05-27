package com.redmagic.undefinedapi.extension

import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections
import org.reflections.scanners.FieldAnnotationsScanner
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.ResourcesScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ConfigurationBuilder
import java.lang.reflect.Method

/**
 * Finds all classes in the specified package that are annotated with the given annotation.
 *
 * @param annotation the annotation to filter the classes by
 * @return a set of classes annotated with the given annotation
 */
fun JavaPlugin.findClasses(annotation: Class<out Annotation>): Set<Class<*>> = Reflections(
    this::class.java.packageName,
    TypeAnnotationsScanner()
).getTypesAnnotatedWith(annotation)

/**
 * Finds all methods annotated with the specified annotation.
 *
 * @param annotation the annotation class to search for
 * @return a mutable set of methods annotated with the specified annotation
 */
fun JavaPlugin.findMethods(annotation: Class<out Annotation>): MutableSet<Method> = Reflections(
    this::class.java.packageName,
    MethodAnnotationsScanner()
).getMethodsAnnotatedWith(annotation)