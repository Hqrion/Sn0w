/*
 * Decompiled with CFR 0.151.
 */
package javassist.compiler.ast;

import javassist.compiler.CompileError;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.CastExpr;
import javassist.compiler.ast.Visitor;

public class InstanceOfExpr
extends CastExpr {
    public InstanceOfExpr(ASTList className, int dim, ASTree expr) {
        super(className, dim, expr);
    }

    public InstanceOfExpr(int type2, int dim, ASTree expr) {
        super(type2, dim, expr);
    }

    @Override
    public String getTag() {
        return "instanceof:" + this.castType + ":" + this.arrayDim;
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atInstanceOfExpr(this);
    }
}

