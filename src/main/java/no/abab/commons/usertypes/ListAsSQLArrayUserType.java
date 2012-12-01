package no.abab.commons.usertypes;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public abstract class ListAsSQLArrayUserType<T> implements UserType {
    public int[] sqlTypes() {
        return new int[]{Types.ARRAY};
    }

    public Object deepCopy(Object value) {
        return MyArrayUtils.cloneArray(value);
    }

    public boolean isMutable() {
        return true;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Array sqlArray = rs.getArray(names[0]);
        if (rs.wasNull())
            return null;

        return arrayToList(sqlArray.getArray());
    }

    private List<T> arrayToList(Object array) {
        Class<?> componentType = array.getClass().getComponentType();
        if (componentType.isPrimitive())
            componentType = ClassUtils.primitiveToWrapper(componentType);
        List<?> list = Arrays.asList(MyArrayUtils.toObjectArray(array));
        return (List<T>) list;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (null == value)
            st.setNull(index, Types.ARRAY);
        else
            st.setArray(index, getDataAsArray(value));
    }

    private Array getDataAsArray(Object array) {
        List<T> list = arrayToList(array);
        return SqlArray.of(list, MyArrayUtils.arrayObjectType(array));
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x == null)
            return 0;
        return ArrayUtils.hashCode(x);
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        ArrayUtils.isEquals(x, y);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}