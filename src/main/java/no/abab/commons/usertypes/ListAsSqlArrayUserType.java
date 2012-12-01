package no.abab.commons.usertypes;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class ListAsSqlArrayUserType<T> implements ParameterizedType, UserType {
    private Class<T> type;

    public int[] sqlTypes() {
        return new int[]{Types.ARRAY};
    }

    @Override
    public Class returnedClass() {
        return List.class;
    }

    public Object deepCopy(Object value) {
        return MyArrayUtils.clone(value);
    }

    public boolean isMutable() {
        return true;
    }

    @Override
    public List<T> nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Array sqlArray = rs.getArray(names[0]);
        if (rs.wasNull())
            return null;

        return (List<T>) MyArrayUtils.toList(sqlArray.getArray());
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (null == value) {
            st.setNull(index, Types.ARRAY);
        } else {
            if (session.getFactory().getDialect().getClass() == H2Dialect.class) {
                st.setObject(index, MyArrayUtils.toList(value).toArray());
            } else {
                st.setArray(index, getDataAsArray(value));
            }
        }
    }

    private Array getDataAsArray(Object array) {
        List<?> list = MyArrayUtils.toList(array);
        return SqlArray.of(list, type);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x == null)
            return 0;
        return ArrayUtils.hashCode(x);
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ArrayUtils.isEquals(x, y);
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

    @Override
    public void setParameterValues(Properties parameters) {
        String type = (String) parameters.get("type");
        if (type == null)
            throw new IllegalStateException("Missing required property 'type'");
        try {
            this.type = (Class<T>) Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}