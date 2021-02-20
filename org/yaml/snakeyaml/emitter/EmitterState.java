/*
 * Decompiled with CFR 0.151.
 */
package org.yaml.snakeyaml.emitter;

import java.io.IOException;

interface EmitterState {
    public void expect() throws IOException;
}

