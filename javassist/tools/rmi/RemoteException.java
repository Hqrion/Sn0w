/*
 * Decompiled with CFR 0.151.
 */
package javassist.tools.rmi;

public class RemoteException
extends RuntimeException {
    public RemoteException(String msg) {
        super(msg);
    }

    public RemoteException(Exception e) {
        super("by " + e.toString());
    }
}

