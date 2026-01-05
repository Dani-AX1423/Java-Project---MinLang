package org.dani.lytrix.core.errors.exceptions;

import org.dani.lytrix.core.errors.exceptions.LyTrixException;
public class InterpreterException extends LyTrixException
{
    public InterpreterException(String errMsg, int line) {
        super("Interpreter", errMsg, line);
    }    
}
