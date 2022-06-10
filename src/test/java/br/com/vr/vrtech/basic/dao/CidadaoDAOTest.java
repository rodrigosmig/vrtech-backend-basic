package br.com.vr.vrtech.basic.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.vr.vrtech.basic.jdbc.ConnectionFactory;
import br.com.vr.vrtech.basic.models.Cidadao;
import br.com.vr.vrtech.basic.models.Logradouro;

class CidadaoDAOTest {
	
	private CidadaoDAO dao;
	private Connection con;

	@BeforeEach
	void setUp() throws Exception {
		this.con = (new ConnectionFactory("test")).getConnection();
		this.dao = new CidadaoDAO(con);
		
		this.createTestsDatabase();
		
		this.con.setAutoCommit(false);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.con.rollback();	
		this.con.close();
	}

	@Test
	void deveriaBuscarApenasCidadaosDoSudeste() {
		List<Logradouro> logradouros = this.getLogradouros();
		List<Cidadao> cidadaos = this.getCidadaos(logradouros);
		
		cidadaos.forEach(cidadao -> dao.insert(cidadao));
		
		List<Cidadao> cidadaosDoSudeste = dao.buscarSomenteOsDaRegiaoSudeste();
		
		assertEquals(1, cidadaosDoSudeste.size());
	}
	
	@Test
	void deveriaBuscarApenasCidadaosMaioreDeTrintaAnos() {
		List<Logradouro> logradouros = this.getLogradouros();
		List<Cidadao> cidadaos = this.getCidadaos(logradouros);
		
		cidadaos.forEach(cidadao -> dao.insert(cidadao));
		
		List<Cidadao> cidadaosMaiores = dao.buscarSomenteOsMaioresDe30();
		
		assertEquals(2, cidadaosMaiores.size());
	}
	
	private List<Logradouro> getLogradouros() {
		List<Logradouro> logradouros = new ArrayList<>();
		String[] estados = new String[]{"PB", "BA", "RJ", "AM"};
		
		for (int i = 0; i < estados.length; i++) {
			Logradouro logradouro = new Logradouro("Logradouro " + (i + 1), estados[i]);
			logradouros.add(logradouro);
		}
		
		return logradouros;
	}
	
	private List<Cidadao> getCidadaos(List<Logradouro> logradouros) {
		List<Cidadao> cidadaos = new ArrayList<>();
		int counter = 1;
		
		for (Logradouro logradouro: logradouros) {
			Cidadao cidadao = new Cidadao();
			cidadao.setNome("Test" + counter);
			cidadao.setIdade(28 + counter);
			cidadao.setLogradouro(logradouro);
			
			cidadaos.add(cidadao);
			
			counter++;
		}

		return cidadaos;
	}
	
	private void createTestsDatabase() throws SQLException {
		Statement stmt = this.con.createStatement(); 
        String sqlLogradouros =  "create table if not exists logradouros( " + 
           "id int auto_increment not null, " + 
           "municipio varchar(60) not null, " +  
           "estado varchar(2) not null, " +  
           "primary key (id))";
        
        String sqlPessoas =  "create table if not exists pessoas( " + 
                "id int auto_increment not null, " + 
                "nome varchar(100) not null, " +  
                "idade int not null, " +  
                "logradouro_id int not null," +
                "primary key (id)," +
                "foreign key (logradouro_id) references logradouros(id))";
        stmt.executeUpdate(sqlLogradouros);
        stmt.executeUpdate(sqlPessoas);
	}
}
