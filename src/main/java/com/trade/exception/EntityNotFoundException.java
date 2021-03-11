package com.trade.exception;

public class EntityNotFoundException extends RuntimeException {

    private String hint;

    public EntityNotFoundException(String message, String hint) {
        super(message);
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
