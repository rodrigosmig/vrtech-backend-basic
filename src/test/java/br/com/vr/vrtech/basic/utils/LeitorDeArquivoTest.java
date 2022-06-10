package br.com.vr.vrtech.basic.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.vr.vrtech.basic.exceptions.ArquivoNaoEncontradoException;
import br.com.vr.vrtech.basic.models.Cidadao;

class LeitorDeArquivoTest {
	
	private String fileName;
	private File file;
	private LeitorDeArquivo reader;

	@BeforeEach
	void setUp() throws Exception {
		this.fileName = "test.txt";
		this.file = new File(fileName);
		this.file.createNewFile();
		
		this.reader = new LeitorDeArquivo(fileName);
	}

	@AfterEach
	void tearDown() throws Exception {
		deleteTestFile(this.fileName);
	}

	@Test
	void deveriaRetornarUmaCollectionVaziaQuandoLerUmArquivoVazio() throws IOException {
		List<Cidadao> cidadaos = this.reader.readFile();
		
		Assertions.assertEquals(0, cidadaos.size());
	}
	
	@Test
	void deveriaLancarUmaExcecaoQuandoTentarLerUmArquivoQueNaoExiste() {
		String filename = "NoFileTest.txt";		try {
			this.reader = new LeitorDeArquivo(filename);
			fail("Não lançou a exceção");
		} catch (ArquivoNaoEncontradoException e) {
			assertEquals("Arquivo não encontrado: " + filename, e.getMessage());
		}			
	}
	
	@Test
	void deveriaLerUmArquivoComUmaLinhaERetornarUmaCollectionDeTamanhoUm() throws IOException, ArquivoNaoEncontradoException {
		this.reader.close();
		String newFile = "newfile.txt";

		String header = "Nome | Idade | Município onde mora | Estado ";
		
		String[] persons = new String[]{"Test name", "21", "Municipio Test", "PB"};
		
		String line = String.join("|", persons);
		
		StringBuilder builder = new StringBuilder(header);
		builder.append(System.lineSeparator());
		builder.append(line);
		
		writeOnFile(builder.toString(), newFile);
		
		this.reader = new LeitorDeArquivo(newFile);
		
		List<Cidadao> cidadaos = this.reader.readFile();
		
		deleteTestFile(newFile);
		
		assertEquals(1, cidadaos.size());
		assertEquals(persons[0], cidadaos.get(0).getNome());
		assertEquals(persons[1], String.valueOf(cidadaos.get(0).getIdade()));
		assertEquals(persons[2], String.valueOf(cidadaos.get(0).getLogradouro().getMunicipio()));
		assertEquals(persons[3], String.valueOf(cidadaos.get(0).getLogradouro().getEstado()));
	}
	
	@Test
	void deveriaLerUmArquivoComQuatroLinhaERetornarUmaCollectionDeTamanhoQuatro() throws IOException, ArquivoNaoEncontradoException {
		this.reader.close();
		String newFile = "newfile.txt";

		String header = "Nome | Idade | Município onde mora | Estado ";
		
		String[][] persons = new String[][]{
			{"Test name 1", "21", "Municipio Test 1", "PB"},
			{"Test name 2", "22", "Municipio Test 2", "MG"},
			{"Test name 3", "23", "Municipio Test 3", "BA"},
			{"Test name 4", "24", "Municipio Test 4", "PE"}
		};
		
		StringBuilder builder = new StringBuilder(header);
		
		for (String[] person : persons) {
			String line = String.join("|", person);
			builder.append(System.lineSeparator());
			builder.append(line);
		}
		
		writeOnFile(builder.toString(), newFile);
		
		this.reader = new LeitorDeArquivo(newFile);
		
		List<Cidadao> cidadaos = this.reader.readFile();
		
		deleteTestFile(newFile);
		
		assertEquals(4, cidadaos.size());
		
		assertEquals(persons[0][0], cidadaos.get(0).getNome());
		assertEquals(persons[0][1], String.valueOf(cidadaos.get(0).getIdade()));
		assertEquals(persons[0][2], String.valueOf(cidadaos.get(0).getLogradouro().getMunicipio()));
		assertEquals(persons[0][3], String.valueOf(cidadaos.get(0).getLogradouro().getEstado()));
		
		assertEquals(persons[1][0], cidadaos.get(1).getNome());
		assertEquals(persons[1][1], String.valueOf(cidadaos.get(1).getIdade()));
		assertEquals(persons[1][2], String.valueOf(cidadaos.get(1).getLogradouro().getMunicipio()));
		assertEquals(persons[1][3], String.valueOf(cidadaos.get(1).getLogradouro().getEstado()));
		
		assertEquals(persons[2][0], cidadaos.get(2).getNome());
		assertEquals(persons[2][1], String.valueOf(cidadaos.get(2).getIdade()));
		assertEquals(persons[2][2], String.valueOf(cidadaos.get(2).getLogradouro().getMunicipio()));
		assertEquals(persons[2][3], String.valueOf(cidadaos.get(2).getLogradouro().getEstado()));
		
		assertEquals(persons[3][0], cidadaos.get(3).getNome());
		assertEquals(persons[3][1], String.valueOf(cidadaos.get(3).getIdade()));
		assertEquals(persons[3][2], String.valueOf(cidadaos.get(3).getLogradouro().getMunicipio()));
		assertEquals(persons[3][3], String.valueOf(cidadaos.get(3).getLogradouro().getEstado()));
	}
	
	@Test
	void deveriaRemoverOsEspacosAdicionaisDasStringsDoArquivo() throws IOException, ArquivoNaoEncontradoException {
		this.reader.close();
		String newFile = "newfile.txt";

		String header = "Nome | Idade | Município onde mora | Estado ";
		
		String[] persons = new String[]{"       Test name      ", "    21      ", "    Municipio Test   ", "    PB    "};
		
		String line = String.join("|", persons);
		
		StringBuilder builder = new StringBuilder(header);
		builder.append(System.lineSeparator());
		builder.append(line);
		
		writeOnFile(builder.toString(), newFile);
		
		this.reader = new LeitorDeArquivo(newFile);
		
		List<Cidadao> cidadaos = this.reader.readFile();
		
		deleteTestFile(newFile);
		
		assertEquals(1, cidadaos.size());
		assertEquals(persons[0].trim(), cidadaos.get(0).getNome());
		assertEquals(persons[1].trim(), String.valueOf(cidadaos.get(0).getIdade()));
		assertEquals(persons[2].trim(), String.valueOf(cidadaos.get(0).getLogradouro().getMunicipio()));
		assertEquals(persons[3].trim(), String.valueOf(cidadaos.get(0).getLogradouro().getEstado()));
	}
	
	private void writeOnFile(String content, String newFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(newFile);
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {
			writer.write(content);
			writer.flush();
			fos.close();
		}		
	}
	
	private void deleteTestFile(String filename) throws IOException {
		this.reader.close();
		
		if (this.fileName.equals(filename)) {
			Files.deleteIfExists(this.file.toPath());			
		} else {
			File newFile = new File(filename);
			Files.deleteIfExists(newFile.toPath());
		}
		
	}
}
