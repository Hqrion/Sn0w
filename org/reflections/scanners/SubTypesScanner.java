/*
 * Decompiled with CFR 0.151.
 */
package org.reflections.scanners;

import org.reflections.scanners.AbstractScanner;
import org.reflections.util.FilterBuilder;

public class SubTypesScanner
extends AbstractScanner {
    public SubTypesScanner() {
        this(true);
    }

    public SubTypesScanner(boolean excludeObjectClass) {
        if (excludeObjectClass) {
            this.filterResultsBy(new FilterBuilder().exclude(Object.class.getName()));
        }
    }

    @Override
    public void scan(Object cls) {
        String className = this.getMetadataAdapter().getClassName(cls);
        String superclass = this.getMetadataAdapter().getSuperclassName(cls);
        if (this.acceptResult(superclass)) {
            this.getStore().put((Object)superclass, (Object)className);
        }
        for (String anInterface : this.getMetadataAdapter().getInterfacesNames(cls)) {
            if (!this.acceptResult(anInterface)) continue;
            this.getStore().put((Object)anInterface, (Object)className);
        }
    }
}

