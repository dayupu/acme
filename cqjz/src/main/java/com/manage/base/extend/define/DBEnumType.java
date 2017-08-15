package com.manage.base.extend.define;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.util.ReflectHelper;
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
public class DBEnumType implements UserType, ParameterizedType, Serializable {

    private static final long serialVersionUID = 2837038914146472518L;

    private Class<? super Enum<?>> enumClass;

    private Map<Integer, Object> constantMap = null;

    private static final int[] SQL_TYPES = { Types.INTEGER };

    @Override
    public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClass");
        try {
            enumClass = ReflectHelper.classForName(enumClassName, this.getClass()).asSubclass(Enum.class);
            if (!ReflectHelper.implementsInterface(enumClass, DBEnum.class)) {
                throw new HibernateException("Enum does not implement DBEnum");
            }
        } catch (ClassNotFoundException exception) {
            throw new HibernateException("Enum class not found", exception);
        }
    }

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return enumClass;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }

        if (x == null || y == null) {
            return false;
        }

        return x.equals(y);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object o)
            throws HibernateException, SQLException {
        Integer value = resultSet.getInt(names[0]);
        if (value == null) {
            return null;
        } else {
            return getEnumConstant(value);
        }
    }

    private Object getEnumConstant(Integer value) {
        if (constantMap == null) {
            Map constantMap = new HashMap<Integer, Object>();
            Object[] enumConstants = enumClass.getEnumConstants();
            for (Object enumC : enumConstants) {
                constantMap.put(((DBEnum) enumC).getConstant(), enumC);
            }
            this.constantMap = constantMap;
        }
        return constantMap.get(value);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index,
            SessionImplementor sessionImplementor) throws HibernateException, SQLException {

        if (value == null) {
            preparedStatement.setNull(index, Types.INTEGER);
        } else {
            DBEnum e = (DBEnum) value;
            preparedStatement.setInt(index, e.getConstant());
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
        return (DBEnum) value;
    }

    @Override
    public Object assemble(Serializable cached, Object o) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

}
