package no.abab.commons.usertypes;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

public class MyArrayUtils {
    public static Object[] toObjectArray(Object primitiveArray) {
        if (primitiveArray instanceof boolean[]) {
            return ArrayUtils.toObject((boolean[]) primitiveArray);
        } else if (primitiveArray instanceof byte[]) {
            return ArrayUtils.toObject((byte[]) primitiveArray);
        } else if (primitiveArray instanceof char[]) {
            return ArrayUtils.toObject((char[]) primitiveArray);
        } else if (primitiveArray instanceof double[]) {
            return ArrayUtils.toObject((double[]) primitiveArray);
        } else if (primitiveArray instanceof float[])  {
            return ArrayUtils.toObject((float[])primitiveArray);
        } else if (primitiveArray instanceof int[]) {
            return ArrayUtils.toObject((int[])primitiveArray);
        } else if (primitiveArray instanceof long[])  {
            return ArrayUtils.toObject((long[]) primitiveArray);
        } else if (primitiveArray instanceof short[]) {
            return ArrayUtils.toObject((short[])primitiveArray);
        } else {
            return (Object[]) primitiveArray;
        }
    }

    public static Class<?> arrayObjectType(Object array) {
        Class<?> componentType = array.getClass().getComponentType();
        if(componentType.isPrimitive())
            return  ClassUtils.primitiveToWrapper(componentType);
        return componentType;
    }

    static Object cloneArray(Object value) {
        if(value == null)
            return null;

        if(value instanceof boolean[])
            return ArrayUtils.clone((boolean[]) value);
        if(value instanceof byte[])
            return ArrayUtils.clone((byte[]) value);
        if(value instanceof char[])
            return ArrayUtils.clone((char[]) value);
        if(value instanceof double[])
            return ArrayUtils.clone((double[]) value);
        if(value instanceof float[])
            return ArrayUtils.clone((float[]) value);
        if(value instanceof int[])
            return ArrayUtils.clone((int[]) value);
        if(value instanceof long[])
            return ArrayUtils.clone((long[]) value);
        if(value instanceof short[])
            return ArrayUtils.clone((short[]) value);
        if(value instanceof Object[])
            return ArrayUtils.clone((Object[]) value);

        throw new IllegalStateException("Unexpected type:" + value);
    }
}
