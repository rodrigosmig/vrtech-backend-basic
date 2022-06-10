package br.com.vr.vrtech.basic.models;

public class Logradouro {
	private Integer id;
	private String municipio;
	private String estado;
	
	public Logradouro(String municipio, String estado) {
		this.municipio = municipio;
		this.estado = estado;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMunicipio() {
		return municipio;
	}
	
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "[Logradouro " + "município=" + municipio + ", estado=" + estado + "]";
	}
}
