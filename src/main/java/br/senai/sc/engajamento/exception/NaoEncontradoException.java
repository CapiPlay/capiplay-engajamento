package br.senai.sc.engajamento.exception;

public class NaoEncontradoException extends RuntimeException {
    public NaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
