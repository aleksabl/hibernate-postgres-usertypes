package no.abab.commons.usertypes;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.jdbc4.Jdbc4Array;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.sql.*;

public class SqlArrayUserType implements UserType {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.ARRAY};
    }

    @Override
    public Class<Array> returnedClass() {
        return Array.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null)
            return y == null;
        if (y == null)
            return false;

        if (!(x instanceof Array && y instanceof Array))
            throw new IllegalStateException("Expected objects of type Array. Got " + x + " and " + y);

        try {
            Object a = ((Array) x).getArray();
            Object b = ((Array) y).getArray();
            return ArrayUtils.isEquals(a, b);
        } catch (SQLException e) {
            throw new HibernateException(e);
        }

    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x == null)
            return 0;

        if (!(x instanceof Array))
            throw new IllegalStateException("Expected object of type Array. Got " + x);

        try {
            return ArrayUtils.hashCode(((Array) x).getArray());
        } catch (SQLException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        if (rs.wasNull())
            return null;

        return rs.getArray(0);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null)
            st.setNull(index, Types.ARRAY);
        else {
            st.setArray(index, (Array) value);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        if(value == null)
            return null;

        if (value instanceof Serializable)
            return (Serializable) value;

        if (!(value instanceof Array))
            throw new IllegalStateException("Expected object of type Array. Got " + value);

        try {
            SerialArray serialArray = new SerialArray((Array) value);
            return serialArray;
        } catch (SQLException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
