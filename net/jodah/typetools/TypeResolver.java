/*
 * Decompiled with CFR 0.151.
 */
package net.jodah.typetools;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import sun.misc.Unsafe;

public final class TypeResolver {
    private static final Map<Class<?>, Reference<Map<TypeVariable<?>, Type>>> TYPE_VARIABLE_CACHE = Collections.synchronizedMap(new WeakHashMap());
    private static volatile boolean CACHE_ENABLED = true;
    private static boolean RESOLVES_LAMBDAS;
    private static Method GET_CONSTANT_POOL;
    private static Method GET_CONSTANT_POOL_SIZE;
    private static Method GET_CONSTANT_POOL_METHOD_AT;
    private static final Map<String, Method> OBJECT_METHODS;
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPERS;
    private static final Double JAVA_VERSION;

    private TypeResolver() {
    }

    public static void enableCache() {
        CACHE_ENABLED = true;
    }

    public static void disableCache() {
        TYPE_VARIABLE_CACHE.clear();
        CACHE_ENABLED = false;
    }

    public static <T, S extends T> Class<?> resolveRawArgument(Class<T> type2, Class<S> subType) {
        return TypeResolver.resolveRawArgument(TypeResolver.resolveGenericType(type2, subType), subType);
    }

    public static Class<?> resolveRawArgument(Type genericType, Class<?> subType) {
        Class<?>[] arguments = TypeResolver.resolveRawArguments(genericType, subType);
        if (arguments == null) {
            return Unknown.class;
        }
        if (arguments.length != 1) {
            throw new IllegalArgumentException("Expected 1 argument for generic type " + genericType + " but found " + arguments.length);
        }
        return arguments[0];
    }

    public static <T, S extends T> Class<?>[] resolveRawArguments(Class<T> type2, Class<S> subType) {
        return TypeResolver.resolveRawArguments(TypeResolver.resolveGenericType(type2, subType), subType);
    }

    public static Class<?>[] resolveRawArguments(Type genericType, Class<?> subType) {
        Class[] result;
        block5: {
            Class functionalInterface;
            block6: {
                block4: {
                    result = null;
                    functionalInterface = null;
                    if (RESOLVES_LAMBDAS && subType.isSynthetic()) {
                        Class fi;
                        Class clazz = genericType instanceof ParameterizedType && ((ParameterizedType)genericType).getRawType() instanceof Class ? (Class)((ParameterizedType)genericType).getRawType() : (fi = genericType instanceof Class ? (Class)genericType : null);
                        if (fi != null && fi.isInterface()) {
                            functionalInterface = fi;
                        }
                    }
                    if (!(genericType instanceof ParameterizedType)) break block4;
                    ParameterizedType paramType = (ParameterizedType)genericType;
                    Type[] arguments = paramType.getActualTypeArguments();
                    result = new Class[arguments.length];
                    for (int i = 0; i < arguments.length; ++i) {
                        result[i] = TypeResolver.resolveRawClass(arguments[i], subType, functionalInterface);
                    }
                    break block5;
                }
                if (!(genericType instanceof TypeVariable)) break block6;
                result = new Class[]{TypeResolver.resolveRawClass(genericType, subType, functionalInterface)};
                break block5;
            }
            if (!(genericType instanceof Class)) break block5;
            TypeVariable<Class<T>>[] typeParams = ((Class)genericType).getTypeParameters();
            result = new Class[typeParams.length];
            for (int i = 0; i < typeParams.length; ++i) {
                result[i] = TypeResolver.resolveRawClass(typeParams[i], subType, functionalInterface);
            }
        }
        return result;
    }

    public static Type resolveGenericType(Class<?> type2, Type subType) {
        Type superClass;
        Type result;
        Class rawType = subType instanceof ParameterizedType ? (Class)((ParameterizedType)subType).getRawType() : (Class)subType;
        if (type2.equals(rawType)) {
            return subType;
        }
        if (type2.isInterface()) {
            for (Type superInterface : rawType.getGenericInterfaces()) {
                if (superInterface == null || superInterface.equals(Object.class) || (result = TypeResolver.resolveGenericType(type2, superInterface)) == null) continue;
                return result;
            }
        }
        if ((superClass = rawType.getGenericSuperclass()) != null && !superClass.equals(Object.class) && (result = TypeResolver.resolveGenericType(type2, superClass)) != null) {
            return result;
        }
        return null;
    }

    public static Class<?> resolveRawClass(Type genericType, Class<?> subType) {
        return TypeResolver.resolveRawClass(genericType, subType, null);
    }

    private static Class<?> resolveRawClass(Type genericType, Class<?> subType, Class<?> functionalInterface) {
        if (genericType instanceof Class) {
            return (Class)genericType;
        }
        if (genericType instanceof ParameterizedType) {
            return TypeResolver.resolveRawClass(((ParameterizedType)genericType).getRawType(), subType, functionalInterface);
        }
        if (genericType instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType)genericType;
            Class<?> component = TypeResolver.resolveRawClass(arrayType.getGenericComponentType(), subType, functionalInterface);
            return Array.newInstance(component, 0).getClass();
        }
        if (genericType instanceof TypeVariable) {
            TypeVariable variable = (TypeVariable)genericType;
            genericType = TypeResolver.getTypeVariableMap(subType, functionalInterface).get(variable);
            genericType = genericType == null ? TypeResolver.resolveBound(variable) : TypeResolver.resolveRawClass(genericType, subType, functionalInterface);
        }
        return genericType instanceof Class ? (Class)genericType : Unknown.class;
    }

    private static Map<TypeVariable<?>, Type> getTypeVariableMap(Class<?> targetType, Class<?> functionalInterface) {
        Map<TypeVariable<?>, Type> map;
        Reference<Map<TypeVariable<?>, Type>> ref = TYPE_VARIABLE_CACHE.get(targetType);
        Map<TypeVariable<?>, Type> map2 = map = ref != null ? ref.get() : null;
        if (map == null) {
            Class<?> type2;
            map = new HashMap();
            if (functionalInterface != null) {
                TypeResolver.populateLambdaArgs(functionalInterface, targetType, map);
            }
            TypeResolver.populateSuperTypeArgs(targetType.getGenericInterfaces(), map, functionalInterface != null);
            Type genericType = targetType.getGenericSuperclass();
            for (type2 = targetType.getSuperclass(); type2 != null && !Object.class.equals(type2); type2 = type2.getSuperclass()) {
                if (genericType instanceof ParameterizedType) {
                    TypeResolver.populateTypeArgs((ParameterizedType)genericType, map, false);
                }
                TypeResolver.populateSuperTypeArgs(type2.getGenericInterfaces(), map, false);
                genericType = type2.getGenericSuperclass();
            }
            type2 = targetType;
            while (type2.isMemberClass()) {
                genericType = type2.getGenericSuperclass();
                if (genericType instanceof ParameterizedType) {
                    TypeResolver.populateTypeArgs((ParameterizedType)genericType, map, functionalInterface != null);
                }
                type2 = type2.getEnclosingClass();
            }
            if (CACHE_ENABLED) {
                TYPE_VARIABLE_CACHE.put(targetType, new WeakReference(map));
            }
        }
        return map;
    }

    private static void populateSuperTypeArgs(Type[] types, Map<TypeVariable<?>, Type> map, boolean depthFirst) {
        for (Type type2 : types) {
            if (type2 instanceof ParameterizedType) {
                Type rawType;
                ParameterizedType parameterizedType = (ParameterizedType)type2;
                if (!depthFirst) {
                    TypeResolver.populateTypeArgs(parameterizedType, map, depthFirst);
                }
                if ((rawType = parameterizedType.getRawType()) instanceof Class) {
                    TypeResolver.populateSuperTypeArgs(((Class)rawType).getGenericInterfaces(), map, depthFirst);
                }
                if (!depthFirst) continue;
                TypeResolver.populateTypeArgs(parameterizedType, map, depthFirst);
                continue;
            }
            if (!(type2 instanceof Class)) continue;
            TypeResolver.populateSuperTypeArgs(((Class)type2).getGenericInterfaces(), map, depthFirst);
        }
    }

    private static void populateTypeArgs(ParameterizedType type2, Map<TypeVariable<?>, Type> map, boolean depthFirst) {
        if (type2.getRawType() instanceof Class) {
            Type owner;
            TypeVariable<Class<T>>[] typeVariables = ((Class)type2.getRawType()).getTypeParameters();
            Type[] typeArguments = type2.getActualTypeArguments();
            if (type2.getOwnerType() != null && (owner = type2.getOwnerType()) instanceof ParameterizedType) {
                TypeResolver.populateTypeArgs((ParameterizedType)owner, map, depthFirst);
            }
            for (int i = 0; i < typeArguments.length; ++i) {
                Type existingType;
                TypeVariable variable = typeVariables[i];
                Type typeArgument = typeArguments[i];
                if (typeArgument instanceof Class) {
                    map.put(variable, typeArgument);
                    continue;
                }
                if (typeArgument instanceof GenericArrayType) {
                    map.put(variable, typeArgument);
                    continue;
                }
                if (typeArgument instanceof ParameterizedType) {
                    map.put(variable, typeArgument);
                    continue;
                }
                if (!(typeArgument instanceof TypeVariable)) continue;
                TypeVariable typeVariableArgument = (TypeVariable)typeArgument;
                if (depthFirst && (existingType = map.get(variable)) != null) {
                    map.put(typeVariableArgument, existingType);
                    continue;
                }
                Type resolvedType = map.get(typeVariableArgument);
                if (resolvedType == null) {
                    resolvedType = TypeResolver.resolveBound(typeVariableArgument);
                }
                map.put(variable, resolvedType);
            }
        }
    }

    public static Type resolveBound(TypeVariable<?> typeVariable) {
        Type[] bounds = typeVariable.getBounds();
        if (bounds.length == 0) {
            return Unknown.class;
        }
        Type bound = bounds[0];
        if (bound instanceof TypeVariable) {
            bound = TypeResolver.resolveBound((TypeVariable)bound);
        }
        return bound == Object.class ? Unknown.class : bound;
    }

    private static void populateLambdaArgs(Class<?> functionalInterface, Class<?> lambdaType, Map<TypeVariable<?>, Type> map) {
        if (RESOLVES_LAMBDAS) {
            for (Method m : functionalInterface.getMethods()) {
                Method objectMethod;
                if (TypeResolver.isDefaultMethod(m) || Modifier.isStatic(m.getModifiers()) || m.isBridge() || (objectMethod = OBJECT_METHODS.get(m.getName())) != null && Arrays.equals(m.getTypeParameters(), objectMethod.getTypeParameters())) continue;
                Type returnTypeVar = m.getGenericReturnType();
                Type[] paramTypeVars = m.getGenericParameterTypes();
                Member member = TypeResolver.getMemberRef(lambdaType);
                if (member == null) {
                    return;
                }
                if (returnTypeVar instanceof TypeVariable) {
                    Class<Object> returnType = member instanceof Method ? ((Method)member).getReturnType() : ((Constructor)member).getDeclaringClass();
                    if (!(returnType = TypeResolver.wrapPrimitives(returnType)).equals(Void.class)) {
                        map.put((TypeVariable)returnTypeVar, returnType);
                    }
                }
                Class<?>[] arguments = member instanceof Method ? ((Method)member).getParameterTypes() : ((Constructor)member).getParameterTypes();
                int paramOffset = 0;
                if (paramTypeVars.length > 0 && paramTypeVars[0] instanceof TypeVariable && paramTypeVars.length == arguments.length + 1) {
                    Class<?> instanceType = member.getDeclaringClass();
                    map.put((TypeVariable)paramTypeVars[0], instanceType);
                    paramOffset = 1;
                }
                int argOffset = 0;
                if (paramTypeVars.length < arguments.length) {
                    argOffset = arguments.length - paramTypeVars.length;
                }
                int i = 0;
                while (i + argOffset < arguments.length) {
                    if (paramTypeVars[i] instanceof TypeVariable) {
                        map.put((TypeVariable)paramTypeVars[i + paramOffset], TypeResolver.wrapPrimitives(arguments[i + argOffset]));
                    }
                    ++i;
                }
                return;
            }
        }
    }

    private static boolean isDefaultMethod(Method m) {
        return JAVA_VERSION >= 1.8 && m.isDefault();
    }

    private static Member getMemberRef(Class<?> type2) {
        Object constantPool;
        try {
            constantPool = GET_CONSTANT_POOL.invoke(type2, new Object[0]);
        }
        catch (Exception ignore) {
            return null;
        }
        Member result = null;
        for (int i = TypeResolver.getConstantPoolSize(constantPool) - 1; i >= 0; --i) {
            Member member = TypeResolver.getConstantPoolMethodAt(constantPool, i);
            if (member == null || member instanceof Constructor && member.getDeclaringClass().getName().equals("java.lang.invoke.SerializedLambda") || member.getDeclaringClass().isAssignableFrom(type2)) continue;
            result = member;
            if (!(member instanceof Method) || !TypeResolver.isAutoBoxingMethod((Method)member)) break;
        }
        return result;
    }

    private static boolean isAutoBoxingMethod(Method method) {
        Class<?>[] parameters = method.getParameterTypes();
        return method.getName().equals("valueOf") && parameters.length == 1 && parameters[0].isPrimitive() && TypeResolver.wrapPrimitives(parameters[0]).equals(method.getDeclaringClass());
    }

    private static Class<?> wrapPrimitives(Class<?> clazz) {
        return clazz.isPrimitive() ? PRIMITIVE_WRAPPERS.get(clazz) : clazz;
    }

    private static int getConstantPoolSize(Object constantPool) {
        try {
            return (Integer)GET_CONSTANT_POOL_SIZE.invoke(constantPool, new Object[0]);
        }
        catch (Exception ignore) {
            return 0;
        }
    }

    private static Member getConstantPoolMethodAt(Object constantPool, int i) {
        try {
            return (Member)GET_CONSTANT_POOL_METHOD_AT.invoke(constantPool, i);
        }
        catch (Exception ignore) {
            return null;
        }
    }

    static {
        OBJECT_METHODS = new HashMap<String, Method>();
        JAVA_VERSION = Double.parseDouble(System.getProperty("java.specification.version", "0"));
        try {
            Unsafe unsafe = AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>(){

                @Override
                public Unsafe run() throws Exception {
                    Field f = Unsafe.class.getDeclaredField("theUnsafe");
                    f.setAccessible(true);
                    return (Unsafe)f.get(null);
                }
            });
            GET_CONSTANT_POOL = Class.class.getDeclaredMethod("getConstantPool", new Class[0]);
            String constantPoolName = JAVA_VERSION < 9.0 ? "sun.reflect.ConstantPool" : "jdk.internal.reflect.ConstantPool";
            Class<?> constantPoolClass = Class.forName(constantPoolName);
            GET_CONSTANT_POOL_SIZE = constantPoolClass.getDeclaredMethod("getSize", new Class[0]);
            GET_CONSTANT_POOL_METHOD_AT = constantPoolClass.getDeclaredMethod("getMethodAt", Integer.TYPE);
            Field overrideField = AccessibleObject.class.getDeclaredField("override");
            long overrideFieldOffset = unsafe.objectFieldOffset(overrideField);
            unsafe.putBoolean((Object)GET_CONSTANT_POOL, overrideFieldOffset, true);
            unsafe.putBoolean((Object)GET_CONSTANT_POOL_SIZE, overrideFieldOffset, true);
            unsafe.putBoolean((Object)GET_CONSTANT_POOL_METHOD_AT, overrideFieldOffset, true);
            Object constantPool = GET_CONSTANT_POOL.invoke(Object.class, new Object[0]);
            GET_CONSTANT_POOL_SIZE.invoke(constantPool, new Object[0]);
            for (Method method : Object.class.getDeclaredMethods()) {
                OBJECT_METHODS.put(method.getName(), method);
            }
            RESOLVES_LAMBDAS = true;
        }
        catch (Exception unsafe) {
            // empty catch block
        }
        HashMap<Class<Object>, Class<Void>> types = new HashMap<Class<Object>, Class<Void>>();
        types.put(Boolean.TYPE, Boolean.class);
        types.put(Byte.TYPE, Byte.class);
        types.put(Character.TYPE, Character.class);
        types.put(Double.TYPE, Double.class);
        types.put(Float.TYPE, Float.class);
        types.put(Integer.TYPE, Integer.class);
        types.put(Long.TYPE, Long.class);
        types.put(Short.TYPE, Short.class);
        types.put(Void.TYPE, Void.class);
        PRIMITIVE_WRAPPERS = Collections.unmodifiableMap(types);
    }

    public static final class Unknown {
        private Unknown() {
        }
    }
}

