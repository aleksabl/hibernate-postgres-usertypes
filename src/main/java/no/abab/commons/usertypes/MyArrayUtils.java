package no.abab.commons.usertypes;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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

    static Object clone(Object value) {
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
        if(value instanceof Collection) {
            return new LinkedList<Object>((Collection<?>) value);
        }
        if(value instanceof Cloneable) {
            try {
                Method method = value.getClass().getMethod("clone");
                return method.invoke(value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        throw new IllegalStateException("Unexpected type:" + value.getClass() + "(" + value + ")");
    }

    public static List<?> toList(Object o) {
        if (o == null)
            return Collections.emptyList();
        if(o instanceof List)
            return (List<?>) o;
        else if (o instanceof Collection)
            return new LinkedList<Object>((Collection<?>) o);
        else if (o.getClass().isArray()) {
            Class<?> componentType = o.getClass().getComponentType();
            if (componentType.isPrimitive())
                componentType = ClassUtils.primitiveToWrapper(componentType);
            List<?> list = Arrays.asList(MyArrayUtils.toObjectArray(o));
            return (List<?>) list;
        } else {
            throw new IllegalArgumentException("Unexpected type of argument: " + o.getClass() + "(" + o + ")");
        }
    }

}
