package dev.fernando.moneyapi.exception;

public class PessoaInativaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PessoaInativaException (String message) {
        super(message);
    }
}
