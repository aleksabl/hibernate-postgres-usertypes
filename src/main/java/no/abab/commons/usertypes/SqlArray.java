package no.abab.commons.usertypes;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SqlArray<T> implements Array {

    private List<T> data;

    private int baseType;

    protected SqlArray(List<T> data, int baseType) {
        this.data = data;
        this.baseType = baseType;
    }

    public static <S> SqlArray<S> of(List<S> data, Class<?> clazz) {
        if(clazz == Boolean.class) {
            return new SqlArray<S>(data, Types.BIT);
        }
        else if(clazz == Integer.class) {
            return new SqlArray<S>(data, Types.INTEGER);
        }
        else if (clazz == Float.class) {
            return new SqlArray<S>(data, Types.FLOAT);
        }
        else if (clazz == Double.class) {
            return new SqlArray<S>(data, Types.DOUBLE);
        }
        else if (clazz == String.class) {
            return new SqlArray<S>(data, Types.VARCHAR);
        }
        else if (clazz == Date.class) {
            return new SqlArray<S>(data, Types.TIMESTAMP);
        }

        throw new IllegalArgumentException("Unkown type: " + clazz);
    }


    @Override
    public String getBaseTypeName() {
        //FIXME when is this necessary?
        return null;
    }

    @Override
    public int getBaseType() {
        return baseType;
    }

    @Override
    public Object getArray() {
        return data.toArray();
    }

    @Override
    public Object getArray(long index, int count) {
        int lastIndex = count - (int) index;
        if (lastIndex > data.size())
            lastIndex = data.size();

        return data.subList((int) (index - 1), lastIndex).toArray();
    }

    @SuppressWarnings("unused")
    public Object getArray(Map<String, Class<?>> map) {
        //FIXME implement this
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getArray(long index, int count, java.util.Map<String, Class<?>> map) {
        //FIXME implement this
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getResultSet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getResultSet(Map<String, Class<?>> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getResultSet(long index, int count) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getResultSet(long index, int count, java.util.Map<String,Class<?>> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void free() {
        //FIXME implement
    }

    @Override
    public String toString() {
        return data.toString();
    }

}