package br.com.vr.vrtech.basic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.vr.vrtech.basic.dao.CidadaoDAO;
import br.com.vr.vrtech.basic.exceptions.ArquivoNaoEncontradoException;
import br.com.vr.vrtech.basic.exceptions.SQLRuntimeException;
import br.com.vr.vrtech.basic.jdbc.ConnectionFactory;
import br.com.vr.vrtech.basic.models.Cidadao;
import br.com.vr.vrtech.basic.utils.LeitorDeArquivo;

public class TestaCidadao {

	public static void main(String[] args) {
		
		try {
			LeitorDeArquivo reader = new LeitorDeArquivo("cidadaos.txt");
			Connection con = (new ConnectionFactory("dev")).getConnection();
			CidadaoDAO cidadaoDao = new CidadaoDAO(con);
			
			List<Cidadao> cidadaos = reader.readFile();
			
			cidadaos.forEach(cidadaoDao::insert);
			
			System.out.println("Cidadãos da região sudeste:");			
			cidadaoDao.buscarSomenteOsDaRegiaoSudeste().forEach(System.out::println);
			
			System.out.println("Cidadãos maiores de 30 anos:");			
			cidadaoDao.buscarSomenteOsMaioresDe30().forEach(System.out::println);
		} catch (ArquivoNaoEncontradoException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo");
		} catch (SQLException e) {
			System.out.println("Erro de conexão do banco de dados");
		} catch (SQLRuntimeException e) {
			System.out.println("Erro ao executar o SQL: " + e.getMessage());
		}
	}

}
