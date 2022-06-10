package br.com.vr.vrtech.basic.models;

public class Cidadao {
	
	private Integer id;
	private String nome;
	private Integer idade;
	private Logradouro logradouro;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	@Override
	public String toString() {
		return "[Cidadao " + "nome=" + nome + ", idade=" + idade + ", município=" + logradouro.getMunicipio() + ", estado=" + logradouro.getEstado() + "]";
	}
}
