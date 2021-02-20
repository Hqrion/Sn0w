/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.util;

import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

public class ClassFinder {
    public static Set<Class> findClasses(String pack, Class subType) {
        Reflections reflections = new Reflections(pack, new Scanner[0]);
        return reflections.getSubTypesOf(subType);
    }
}

