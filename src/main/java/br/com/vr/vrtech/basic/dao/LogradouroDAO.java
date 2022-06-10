package br.com.vr.vrtech.basic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.vr.vrtech.basic.exceptions.SQLRuntimeException;
import br.com.vr.vrtech.basic.models.Logradouro;

public class LogradouroDAO {
	
	private Connection connection;

	public LogradouroDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void insert(Logradouro logradouro) {
		String sql = "INSERT INTO logradouros (municipio, estado) VALUES (?, ?)";
		
		try {
			try(PreparedStatement pstm = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstm.setString(1, logradouro.getMunicipio());
				pstm.setString(2, logradouro.getEstado());
				
				pstm.execute();
				
				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						logradouro.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e.getMessage());
		}
	}
}
