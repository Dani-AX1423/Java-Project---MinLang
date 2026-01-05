package org.dani.lytrix.core.errors.exceptions;


import java.lang.RuntimeException;


public abstract class LyTrixException extends RuntimeException {

    //Line which contains error;
    protected final int line;
    protected final String phase;
    
    public LyTrixException(String phase, String errMsg, int line) {
        
        super(errMsg);
        this.phase=phase;
        this.line=line;
    }

    public String format() {
        return "[ERR] [PHASE:" + phase +  "] Line " + line + " " + getMessage();
    }
}
