package org.dani.lytrix.core.frontend.scanner.Lexing_Process;

import java.util.*;
import java.io.*;
import org.dani.lytrix.core.frontend.scanner.tokens.Token;
import org.dani.lytrix.core.frontend.scanner.tokens.TokenType;
//import org.dani.lytrix.core.frontend.scanner.tokens.*;

public class LexHelperFunctions {
    protected String SrcCode;
    protected int idx;
    protected int R;
    protected int C;

    protected LexHelperFunctions(String SrcCode) {
        this.SrcCode = SrcCode;
        this.idx = 0;
        this.R = 1;
        this.C = 1;
    }

    // Helper functions definitons //
    // End of file reached or not
    protected boolean isEOF() {
        return idx >= SrcCode.length();
    }

    // walk in and retriever functions
    protected char peek() {
        char c;
        if (isEOF())
            return '\0';

        else
            c = SrcCode.charAt(idx);
        return c;
    }

    protected char advance() {

        if (isEOF())
            return '\0';
        char c = peek();
        if (c == '\n') {
            R++;
            C = 1;
        } else
            C++;

        idx++;

        return c;
    }

    // current character checker functions
    protected boolean isAlpha(char c) {
        return Character.isAlphabetic(c);
    }

    protected boolean isSQuot(char c) {
        return (c == '\'');
    }

    protected boolean isDQuot(char c) {
        return (c == '\"');
    }

    protected boolean isSpace(char c) {
        return Character.isWhitespace(c);
    }

    // 0.0.1 - 0.0.3 versions don't require this
    //sorry isdigit() is actually getting used hahaha.

    protected boolean isAlnum(char c) {
        return (Character.isAlphabetic(c) || Character.isDigit(c));
    }

    protected boolean isDigit(char c) {
        return Character.isDigit(c);
    }
    //
    //

    // checks if it is a operator:
    protected boolean isOpt(char c) {
        if(c=='+') 
            return true;
        else if(c=='-')
            return true;
        else if(c=='*')
            return true;
        else if(c=='/')
            return true;
        else if(c=='%')
            return true;
        else 
            return false;
    }


    


    //...
    //.
    // Helper functions that read specific type of literal values

    //
    // Iterator function to read Token using loop
    protected String readToken() {
        char c = peek();
        StringBuilder curr = new StringBuilder();
        while (!isEOF() && isAlnum(c)) {
            curr.append(c);
            advance();
            c = peek();
        }

        return curr.toString();
    }

    protected String readChar() {
        advance();
        char c=peek();
        advance();
        if(isEOF() || c=='\n')
        {throw new RuntimeException("missing quotes for character termination");}
        if(!isSQuot(peek()))
        {throw new RuntimeException("only one character can be allowed in character literal");}
        else
        {
            advance();
            return Character.toString(c);
        }
    }

    protected String readString() {
        char c = peek();
        StringBuilder curr = new StringBuilder();
        if (isDQuot(c)) {
            advance();
            c = peek();
        }
        while (!isEOF()) {
            if (c == '\n') {
                throw new RuntimeException("missing quotes for character termination");
            }
            if (isDQuot(c)) {
                advance();
                break;
            }
            curr.append(c);
            advance();
            c = peek();
        }
        return curr.toString();
    }

    protected String readNumber() {
        StringBuilder curr = new StringBuilder();
        char c=peek();
        boolean pt = false;
        if(c=='.')
        {curr.append('0');}
        while(!isEOF() && (isDigit(c) || c=='.') && c!=';')
        {
            //c = peek();
            if(isDigit(c))
            {
                curr.append(c);
                advance();
                c=peek();
                continue;
            }
            else if(c=='.')
            {
                if(!pt)
                {   
                    pt = true;
                    curr.append(c);
                    advance();
                    c=peek();
                    if(isDigit(c))
                    {
                        continue;
                    }
                    else
                    {
                        throw new RuntimeException("expected numeric values after decimal point");
                    }
                    //continue;
                }
                else
                {
                    throw new RuntimeException("cannot have 2 decimal points");
                }
            }
            //pt = false;
        
        }
        pt=false;
        return curr.toString();
    }


}
