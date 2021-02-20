/*
 * Decompiled with CFR 0.151.
 */
package org.reflections.scanners;

import org.reflections.scanners.AbstractScanner;
import org.reflections.vfs.Vfs;

@Deprecated
public class TypesScanner
extends AbstractScanner {
    @Override
    public Object scan(Vfs.File file, Object classObject) {
        classObject = super.scan(file, classObject);
        String className = this.getMetadataAdapter().getClassName(classObject);
        this.getStore().put((Object)className, (Object)className);
        return classObject;
    }

    @Override
    public void scan(Object cls) {
        throw new UnsupportedOperationException("should not get here");
    }
}

