/*
 * Decompiled with CFR 0.151.
 */
package org.reflections.scanners;

import java.lang.annotation.Inherited;
import org.reflections.scanners.AbstractScanner;

public class TypeAnnotationsScanner
extends AbstractScanner {
    @Override
    public void scan(Object cls) {
        String className = this.getMetadataAdapter().getClassName(cls);
        for (String annotationType : this.getMetadataAdapter().getClassAnnotationNames(cls)) {
            if (!this.acceptResult(annotationType) && !annotationType.equals(Inherited.class.getName())) continue;
            this.getStore().put((Object)annotationType, (Object)className);
        }
    }
}

