package br.com.vr.vrtech.basic.exceptions;

public class ArquivoNaoEncontradoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ArquivoNaoEncontradoException() {
		super("Arquivo n�o encontrado");
	}
	
	public ArquivoNaoEncontradoException(String fileName) {
		super("Arquivo n�o encontrado: " + fileName);
	}
}
