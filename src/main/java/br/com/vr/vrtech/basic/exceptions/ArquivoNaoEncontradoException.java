package br.com.vr.vrtech.basic.exceptions;

public class ArquivoNaoEncontradoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ArquivoNaoEncontradoException() {
		super("Arquivo não encontrado");
	}
	
	public ArquivoNaoEncontradoException(String fileName) {
		super("Arquivo não encontrado: " + fileName);
	}
}
