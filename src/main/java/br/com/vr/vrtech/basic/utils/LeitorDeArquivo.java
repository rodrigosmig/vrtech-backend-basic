package br.com.vr.vrtech.basic.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.vr.vrtech.basic.exceptions.ArquivoNaoEncontradoException;
import br.com.vr.vrtech.basic.models.Cidadao;
import br.com.vr.vrtech.basic.models.Logradouro;

public class LeitorDeArquivo {
	private FileInputStream fis;
	private BufferedReader reader;
	private List<Cidadao> cidadaos;

	public LeitorDeArquivo(String fileName) throws ArquivoNaoEncontradoException {		
		try {
			this.fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			throw new ArquivoNaoEncontradoException(fileName);
		}
		
		this.reader = new BufferedReader(new InputStreamReader(this.fis));		
		this.cidadaos = new ArrayList<>();
	}

	public List<Cidadao> readFile() throws IOException {
		reader.readLine(); //header
		String line = reader.readLine();
		
		while(line != null) {
			Cidadao cidadao = this.getCidadaoFromString(line);			
			this.cidadaos.add(cidadao);
			
			line = this.reader.readLine();
		}
		
		return this.cidadaos;
	}
	
	private Cidadao getCidadaoFromString(String line) {
		List<String> values = Arrays.asList(line.split("\\|"));
		
		Cidadao cidadao = new Cidadao();
		cidadao.setNome(values.get(0).trim());
		cidadao.setIdade(Integer.valueOf(values.get(1).trim()));
		
		Logradouro logradouro = new Logradouro(values.get(2).trim(), values.get(3).trim());		
		cidadao.setLogradouro(logradouro);
				
		return cidadao;
	}
	
	public void close() throws IOException {
		this.fis.close();
		this.reader.close();
	}
}
