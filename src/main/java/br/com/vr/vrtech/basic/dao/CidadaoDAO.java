package br.com.vr.vrtech.basic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.vr.vrtech.basic.exceptions.SQLRuntimeException;
import br.com.vr.vrtech.basic.models.Cidadao;
import br.com.vr.vrtech.basic.models.Logradouro;

public class CidadaoDAO {
	
	private Connection connection;

	public CidadaoDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void insert(Cidadao cidadao) {
		String sql = "INSERT INTO pessoas (nome, idade, logradouro_id) VALUES (?, ?, ?)";
		
		try {
			try(PreparedStatement pstm = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				createLogradouro(cidadao.getLogradouro());
				
				pstm.setString(1, cidadao.getNome());
				pstm.setInt(2, cidadao.getIdade());
				pstm.setInt(3, cidadao.getLogradouro().getId());
				
				pstm.execute();
				
				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						cidadao.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e.getMessage());
		}
	}
	
	public List<Cidadao> buscarSomenteOsDaRegiaoSudeste() {
		List<Cidadao> cidadaos = new ArrayList<>();
		
		String sql = "SELECT p.*, l.* FROM pessoas p "
				+ "JOIN logradouros l ON l.id = p.logradouro_id "
				+ "WHERE l.estado in ('SP', 'RJ', 'MG', 'ES') ";
		
		try {

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						Logradouro logradouro = this.getLogradouroFromResultSet(rst);
						Cidadao cidadao = this.getCidadaoFromResultSet(rst, logradouro);

						cidadaos.add(cidadao);
					}
				}
			}
			
			return cidadaos;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e.getMessage());
		}
	}
	
	public List<Cidadao> buscarSomenteOsMaioresDe30() {
		List<Cidadao> cidadaos = new ArrayList<>();
		
		String sql = "SELECT p.*, l.* FROM pessoas p "
				+ "JOIN logradouros l ON l.id = p.logradouro_id ";
		
		try {

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						Logradouro logradouro = this.getLogradouroFromResultSet(rst);
						Cidadao cidadao = this.getCidadaoFromResultSet(rst, logradouro);

						cidadaos.add(cidadao);
					}
				}
			}
			
			return cidadaos.stream()
					.filter(cidadao -> cidadao.getIdade() > 30)
					.toList();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e.getMessage());
		}
	}
	
	private Logradouro getLogradouroFromResultSet(ResultSet rst) throws SQLException {
		Logradouro logradouro = new Logradouro(rst.getString(6), rst.getString(7));
		logradouro.setId(rst.getInt(5));
		
		return logradouro;
	}
	
	private Cidadao getCidadaoFromResultSet(ResultSet rst, Logradouro logradouro) throws SQLException {
		Cidadao cidadao = new Cidadao();
		cidadao.setId(rst.getInt(1));
		cidadao.setNome(rst.getString(2));
		cidadao.setIdade(rst.getInt(3));
		cidadao.setLogradouro(logradouro);
		
		return cidadao;
	}

	private void createLogradouro(Logradouro logradouro) throws SQLException {	
		LogradouroDAO logradouroDao = new LogradouroDAO(this.connection);
		logradouroDao.insert(logradouro);
	}
}
