package com.manage.base.extend.define;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * Created by bert on 2017/8/13.
 */
public class DBEnum implements UserType, ParameterizedType, Serializable {

    private static final long serialVersionUID = 2837038914146472518L;

    private Class<DBMapper> clazz;

    private static final int[] SQL_TYPES = {Types.INTEGER};

    public DBEnum() {

    }

    @Override
    public void setParameterValues(Properties parameters) {
        String enumClass = (String) parameters.get("enumClass");
        try {
            clazz = (Class<DBMapper>) Class.forName(enumClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return DBMapper.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }

        if (x == null || y == null) {
            return false;
        }

        return ((DBMapper) x).dbValue().equals(((DBMapper) y).dbValue());
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        Integer dbValue = resultSet.getInt(strings[0]);
        if (dbValue != null) {
            for (DBMapper dbMapper : clazz.getEnumConstants()) {
                if (dbValue.equals(dbMapper.dbValue())) {
                    return dbMapper;
                }
            }
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {

        DBMapper dbMapper = (DBMapper) value;
        if (dbMapper == null) {
            preparedStatement.setNull(i, Types.INTEGER);
        } else {
            preparedStatement.setInt(i, dbMapper.dbValue());
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
    public Serializable disassemble(Object o) throws HibernateException {
        return null;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return null;
    }

    @Override
    public Object replace(Object x, Object y, Object entity) throws HibernateException {
        return x;
    }


}
