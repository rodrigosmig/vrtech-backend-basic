package br.com.vr.vrtech.basic.jdbc;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	public DataSource dataSource;

	public ConnectionFactory(String env) {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		
		if (env.equals("test")) {
			try {
				comboPooledDataSource.setDriverClass("org.h2.Driver");
				comboPooledDataSource.setJdbcUrl("jdbc:h2:mem:testdb");
				comboPooledDataSource.setUser("sa");
				comboPooledDataSource.setPassword("");
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		} else {
			comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/vrtech?useTimezone=true&serverTimezone=UTC");
			comboPooledDataSource.setUser("root");
			comboPooledDataSource.setPassword("rootsql");
			comboPooledDataSource.setMaxPoolSize(15);
		}

		this.dataSource = comboPooledDataSource;
	}
	
	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}
}
