/*
 * Decompiled with CFR 0.151.
 */
package org.reflections.scanners;

import org.reflections.scanners.AbstractScanner;

public class MethodAnnotationsScanner
extends AbstractScanner {
    @Override
    public void scan(Object cls) {
        for (Object method : this.getMetadataAdapter().getMethods(cls)) {
            for (String methodAnnotation : this.getMetadataAdapter().getMethodAnnotationNames(method)) {
                if (!this.acceptResult(methodAnnotation)) continue;
                this.getStore().put((Object)methodAnnotation, (Object)this.getMetadataAdapter().getMethodFullKey(cls, method));
            }
        }
    }
}

