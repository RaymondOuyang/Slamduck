package com.missingvia.slamduck.common.mybatis;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeReference;

/**
 * 修改mybatis源代码 中的BaseTypeHandler.setParameter方法
 * 使得可能为空列设置PreparedStatement参数时,不需要指定列的JdbcType
 * 代码来自spring3.0中org.springframework.jdbc.core.StatementCreatorUtils.
 * setNull(PreparedStatement ps, int paramIndex, int sqlType, String typeName)
 * 在实际使用时，修改该类的包为org.apache.ibatis.type,
 * 编译后将class文件覆盖mybatis-3.2.7.jar中的org.apache.ibatis.type.BaseTypeHandler。
 * 对于其它版本的mybatis，应该拷贝BaseTypeHandler的源代码修改相应地方然后编译覆盖。
 * 这只是解决插入NULL值得方法之一，也可以通过配置<setting name="jdbcTypeForNull" value="NULL" />来解决。
 * 
 * @author Clinton Begin
 * @author Simone Tripodi
 */
public abstract class BaseTypeHandler<T> extends TypeReference<T> implements
		TypeHandler<T> {

	protected Configuration configuration;

	public void setConfiguration(Configuration c) {
		this.configuration = c;
	}

	public void setParameter(PreparedStatement ps, int i, T parameter,
			JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			if (jdbcType == null) {
				boolean useSetObject = false;
				int sqlType = Types.NULL;
				try {
					DatabaseMetaData dbmd = ps.getConnection().getMetaData();
					String databaseProductName = dbmd.getDatabaseProductName();
					String jdbcDriverName = dbmd.getDriverName();
					if (databaseProductName.startsWith("Informix")
							|| jdbcDriverName
									.startsWith("Microsoft SQL Server")) {
						useSetObject = true;
					} else if (databaseProductName.startsWith("DB2")
							|| jdbcDriverName.startsWith("jConnect")
							|| jdbcDriverName.startsWith("SQLServer")
							|| jdbcDriverName.startsWith("Apache Derby")) {
						sqlType = Types.VARCHAR;
					}
				} catch (Throwable ex) {
					throw new TypeException(
							"Could not check database or driver name", ex);
				}
				if (useSetObject) {
					ps.setObject(i, null);
				} else {
					ps.setNull(i, sqlType);
				}

			} else {
				//jdbcType != null
				try {
					ps.setNull(i, jdbcType.TYPE_CODE);
				} catch (SQLException e) {
					throw new TypeException(
							"Error setting null for parameter #"
									+ i
									+ " with JdbcType "
									+ jdbcType
									+ " . "
									+ "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
									+ "Cause: " + e, e);
				}
			}
		} else {
			setNonNullParameter(ps, i, parameter, jdbcType);
		}
	}

	public T getResult(ResultSet rs, String columnName) throws SQLException {
		T result = getNullableResult(rs, columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			return result;
		}
	}

	public T getResult(ResultSet rs, int columnIndex) throws SQLException {
		T result = getNullableResult(rs, columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			return result;
		}
	}

	public T getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		T result = getNullableResult(cs, columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			return result;
		}
	}

	public abstract void setNonNullParameter(PreparedStatement ps, int i,
			T parameter, JdbcType jdbcType) throws SQLException;

	public abstract T getNullableResult(ResultSet rs, String columnName)
			throws SQLException;

	public abstract T getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException;

	public abstract T getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException;

}
