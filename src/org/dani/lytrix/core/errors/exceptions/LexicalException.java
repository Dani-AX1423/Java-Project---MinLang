package org.dani.lytrix.core.errors.exceptions;

import org.dani.lytrix.core.errors.exceptions.LyTrixException;

public class LexicalException extends LyTrixException 
{
    
    public LexicalException(String errMsg, int line) {
        super("Lexing", errMsg, line);
        
    }
}
