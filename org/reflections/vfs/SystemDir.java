/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.AbstractIterator
 *  com.google.common.collect.Lists
 */
package org.reflections.vfs;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.reflections.vfs.SystemFile;
import org.reflections.vfs.Vfs;

public class SystemDir
implements Vfs.Dir {
    private final File file;

    public SystemDir(File file) {
        if (!(file == null || file.isDirectory() && file.canRead())) {
            throw new RuntimeException("cannot use dir " + file);
        }
        this.file = file;
    }

    @Override
    public String getPath() {
        if (this.file == null) {
            return "/NO-SUCH-DIRECTORY/";
        }
        return this.file.getPath().replace("\\", "/");
    }

    @Override
    public Iterable<Vfs.File> getFiles() {
        if (this.file == null || !this.file.exists()) {
            return Collections.emptyList();
        }
        return new Iterable<Vfs.File>(){

            @Override
            public Iterator<Vfs.File> iterator() {
                return new AbstractIterator<Vfs.File>(){
                    final Stack<File> stack = new Stack();
                    {
                        this.stack.addAll(SystemDir.listFiles(SystemDir.this.file));
                    }

                    protected Vfs.File computeNext() {
                        while (!this.stack.isEmpty()) {
                            File file = this.stack.pop();
                            if (file.isDirectory()) {
                                this.stack.addAll(SystemDir.listFiles(file));
                                continue;
                            }
                            return new SystemFile(SystemDir.this, file);
                        }
                        return (Vfs.File)this.endOfData();
                    }
                };
            }
        };
    }

    private static List<File> listFiles(File file) {
        Object[] files = file.listFiles();
        if (files != null) {
            return Lists.newArrayList((Object[])files);
        }
        return Lists.newArrayList();
    }

    @Override
    public void close() {
    }

    public String toString() {
        return this.getPath();
    }
}

