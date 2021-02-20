/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.command.syntax;

import java.util.ArrayList;
import java.util.List;
import me.zeroeightsix.kami.command.syntax.SyntaxChunk;
import me.zeroeightsix.kami.command.syntax.SyntaxParser;

public class ChunkBuilder {
    private static final SyntaxChunk[] EXAMPLE = new SyntaxChunk[0];
    List<SyntaxChunk> chunks = new ArrayList<SyntaxChunk>();

    public ChunkBuilder append(SyntaxChunk syntaxChunk) {
        this.chunks.add(syntaxChunk);
        return this;
    }

    public ChunkBuilder append(String head, boolean necessary) {
        this.append(new SyntaxChunk(head, necessary));
        return this;
    }

    public ChunkBuilder append(String head, boolean necessary, SyntaxParser parser) {
        SyntaxChunk chunk = new SyntaxChunk(head, necessary);
        chunk.setParser(parser);
        this.append(chunk);
        return this;
    }

    public SyntaxChunk[] build() {
        return this.chunks.toArray(EXAMPLE);
    }

    public ChunkBuilder append(String name) {
        return this.append(name, true);
    }
}

