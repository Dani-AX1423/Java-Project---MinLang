package org.dani.lytrix.core.errors.exceptions;

import org.dani.lytrix.core.errors.exceptions.LyTrixException;
public class SyntaxException extends LyTrixException 
{
    
    public SyntaxException(String errMsg, int line)
    {
        super("Parsing", errMsg, line);
    }  
}
